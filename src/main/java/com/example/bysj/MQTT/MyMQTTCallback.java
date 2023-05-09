package com.example.bysj.MQTT;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import com.example.bysj.mapper.StudentInfoMapper;
import com.example.bysj.mapper.TestInfoMapper;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bysj.service.RedisTemplateService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WXY
 * @date 2022/6/29 20:43
 */
@Service
public class MyMQTTCallback implements MqttCallbackExtended {
    //手动注入
    private MqttConfiguration mqttConfiguration = SpringUtils.getBean(MqttConfiguration.class);

    private static final Logger log = LoggerFactory.getLogger(MyMQTTCallback.class);

    private StudentInfoMapper studentInfoMapper = SpringUtils.getBean(StudentInfoMapper.class);

    private TestInfoMapper testInfoMapper = SpringUtils.getBean(TestInfoMapper.class);
    private MyMQTTClient myMQTTClient;
    List<Integer> heartRates = new ArrayList<Integer>();

    public MyMQTTCallback(MyMQTTClient myMQTTClient) {
        this.myMQTTClient = myMQTTClient;
    }

    private RedisTemplateService redisTemplateService = SpringUtils.getBean(RedisTemplateService.class);
    /**
     * 丢失连接，可在这里做重连
     * 只会调用一次
     *
     * @param throwable
     */
    private int num=0;
    @Override
    public void connectionLost(Throwable throwable) {
        log.error("mqtt connectionLost 连接断开，5S之后尝试重连: {}", throwable.getMessage());
        long reconnectTimes = 1;
        while (true) {
            try {
                if (MyMQTTClient.getClient().isConnected()) {
                    //判断已经重新连接成功  需要重新订阅主题 可以在这个if里面订阅主题  或者 connectComplete（方法里面）  看你们自己选择
                    log.warn("mqtt reconnect success end  重新连接  重新订阅成功");
                    return;
                }
                reconnectTimes+=1;
                log.warn("mqtt reconnect times = {} try again...  mqtt重新连接时间 {}", reconnectTimes, reconnectTimes);
                MyMQTTClient.getClient().reconnect();
            } catch (MqttException e) {
                log.error("mqtt断连异常", e);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
            }
        }
    }

    /**
     * @param topic
     * @param mqttMessage
     * @throws Exception
     * subscribe后得到的消息会执行到这里面
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("接收消息主题 : {}，接收消息内容 : {}", topic, new String(mqttMessage.getPayload()));
        //接收设备1主题
        if (topic.equals("v3/798313762/devices/eui-81fa12f400004061/up")){
            Map maps = (Map) JSON.parse(new String(mqttMessage.getPayload(), CharsetUtil.UTF_8));
            String frmPayload = (String) ((Map) maps.get("uplink_message")).get("frm_payload");
            System.out.println(frmPayload);
            byte[] decodedBytes = Base64.getDecoder().decode(frmPayload);
            String decodedString = new String(decodedBytes);
            String firstTwoChars = decodedString.substring(0,2);
            System.out.println(decodedString);
            Pattern pattern = Pattern.compile("^00(\\d+)k(\\d+)$"); // 匹配正则表达式
            Matcher matcher = pattern.matcher(decodedString);
            if (matcher.matches()) {
                Integer num1 = Integer.valueOf(matcher.group(1)); // 获取第一个数字串(心率)
                Integer num2 = Integer.valueOf(matcher.group(2)); // 获取第二个数字串(血氧)
                if(num1>200) num1=num1-100;
                if(num1<50) num1=65;
                if(num2<90){
                    num2=92;
                }
                System.out.println(num1 + ", " + num2); // 输出结果
                heartRates.add(num1);
                Optional<Integer> maxHeartRate = heartRates.stream().max(Integer::compare);
                Optional<Integer> minHeartRate = heartRates.stream().min(Integer::compare);
                Integer avgHeartRate = (int) heartRates.stream().mapToInt(Integer::intValue).average().orElse(0);

                // 设置血氧和心率
                testInfoMapper.updateTestInfoById(1,num2,maxHeartRate.get(),minHeartRate.get(),avgHeartRate,num1);
            }
            else if (firstTwoChars.equals("01")) {
                // 设置状态为跌倒
                System.out.println("跌倒了");
                testInfoMapper.setState();
            }
        }
        //接收设备2主题 绑卡
        if (topic.equals("v3/798313762/devices/eui-81fa12f40000c858/up")){
            Map maps = (Map) JSON.parse(new String(mqttMessage.getPayload(), CharsetUtil.UTF_8));
            String frmPayload = (String) ((Map) maps.get("uplink_message")).get("frm_payload");
            byte[] decodedBytes = Base64.getDecoder().decode(frmPayload);
            String decodedString = new String(decodedBytes);
            System.out.println(decodedString);
            studentInfoMapper.bindBraceletId(Long.parseLong(decodedString));
            num=0;
            heartRates=null;
            testInfoMapper.updateTestInfoById(1,0,0,0,0,0);
        }
    }


    /**
     *连接成功后的回调 可以在这个方法执行 订阅主题  生成Bean的 MqttConfiguration方法中订阅主题 出现bug
     *重新连接后  主题也需要再次订阅  将重新订阅主题放在连接成功后的回调 比较合理
     * @param reconnect
     * @param serverURI
     */
    @Override
    public  void  connectComplete(boolean reconnect,String serverURI){
        log.info("MQTT 连接成功，连接方式：{}",reconnect?"重连":"直连");
        //订阅主题
        myMQTTClient.subscribe(mqttConfiguration.topic1, 0);
        myMQTTClient.subscribe(mqttConfiguration.topic2, 0);
    }

    /**
     * 消息到达后
     * subscribe后，执行的回调函数
     *
     * @param s
     * @param mqttMessage
     * @throws Exception
     */
    /**
     * publish后，配送完成后回调的方法
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("==========deliveryComplete={}==========", iMqttDeliveryToken.isComplete());
    }
}

