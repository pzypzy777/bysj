package com.example.bysj.controller;


import com.example.bysj.MQTT.MqttMsg;
import com.example.bysj.MQTT.MyMQTTClient;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author WXY
 * @date 2022/6/29 20:44
 */
@RestController
@RequestMapping("/sun/mqtt")
public class MqttController {

    @Autowired
    private MyMQTTClient myMQTTClient;

    @Value("${mqtt.topic1}")
    String topic1;
    @Value("${mqtt.topic2}")
    String topic2;



    Queue<String> msgQueue = new LinkedList<>();
    int count = 1;

    @PostMapping("/getMsg")
    @ResponseBody
    public synchronized void mqttMsg(MqttMsg mqttMsg) {
        System.out.println("队列元素数量：" + msgQueue.size());
        System.out.println("***************" + mqttMsg.getName() + ":" + mqttMsg.getContent() + "****************");

        //时间格式化
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        mqttMsg.setTime(time);

        mqttMsg.setContent(mqttMsg.getContent() + "——后台编号：" + count);
        count++;

        //map转json
        JSONObject json = JSONObject.fromObject(mqttMsg);
        String sendMsg = json.toString();
        System.out.println(sendMsg);

        //队列添加元素
        boolean flag = msgQueue.offer(sendMsg);
        if (flag) {
            //发布消息  topic2 是你要发送到那个通道里面的主题 比如我要发送到topic2主题消息
            myMQTTClient.publish(msgQueue.poll(), topic2);
            System.out.println("时间戳" + System.currentTimeMillis());
        }
        System.out.println("队列元素数量：" + msgQueue.size());
    }
}

