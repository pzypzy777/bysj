package com.example.bysj.MQTT;

import lombok.Data;

/**
 * @author WXY
 * @date 2022/6/29 20:44
 */
@Data
public class MqttMsg {
    private String name = "";
    private String content = "";
    private String time = "";

}
