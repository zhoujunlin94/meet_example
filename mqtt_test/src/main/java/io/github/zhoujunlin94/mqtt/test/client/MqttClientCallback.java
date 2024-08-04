package io.github.zhoujunlin94.mqtt.test.client;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSONObject;
import io.github.zhoujunlin94.mqtt.test.handler.MqttMessageHandler;
import io.github.zhoujunlin94.mqtt.test.handler.TestMqttMessageHandler;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024/8/4 21:38
 */
public class MqttClientCallback implements MqttCallback {

    private List<MqttMessageHandler> mqttMessageHandlers;

    public MqttClientCallback() {
        this.mqttMessageHandlers = new LinkedList<>();
        this.mqttMessageHandlers.add(new TestMqttMessageHandler());
    }

    @Override
    public void connectionLost(Throwable cause) {
        Console.log("connectionLost, {}", cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Console.log("messageArrived, topic:{}, message:{}", topic, JSONObject.toJSONString(message));
        mqttMessageHandlers.forEach(mqttMessageHandler -> mqttMessageHandler.handle(topic, message));
    }

    @Override
    @SneakyThrows
    public void deliveryComplete(IMqttDeliveryToken token) {
        String payload = new String(token.getMessage().getPayload());
        Console.log("deliveryComplete, topics:{}, isComplete:{}, payload:{}", token.getTopics(), token.isComplete(), payload);
    }

}
