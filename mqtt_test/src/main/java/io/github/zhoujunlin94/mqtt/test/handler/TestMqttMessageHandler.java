package io.github.zhoujunlin94.mqtt.test.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Console;
import com.alibaba.fastjson2.JSON;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024/8/4 21:40
 */
public class TestMqttMessageHandler implements MqttMessageHandler {

    @Override
    public void handleMessage(String topic, MqttMessage message) {
        Console.log("DemoMqttMessageHandler#handleMessage, topic:{}, message:{}", topic, JSON.toJSONString(message));
        String payload = new String(message.getPayload());
        Console.log("======> \n{}", payload);
    }

    @Override
    public List<String> topics() {
        return CollUtil.newArrayList(
                "mqtt/demo",
                "mqtt/demo2"
        );
    }

}
