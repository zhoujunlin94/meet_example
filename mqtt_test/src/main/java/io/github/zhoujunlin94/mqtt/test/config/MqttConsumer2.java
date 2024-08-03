package io.github.zhoujunlin94.mqtt.test.config;

import cn.hutool.core.util.IdUtil;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author zhoujunlin
 * @date 2024/8/1 21:34
 */
public class MqttConsumer2 {


    @SneakyThrows
    public static void main(String[] args) {
        try {
            MqttClient client = new MqttClient("tcp://broker.emqx.io:1883", IdUtil.fastSimpleUUID(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setKeepAliveInterval(60);
            options.setConnectionTimeout(60);
            options.setAutomaticReconnect(true);
            // 设置回调
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("topic: " + topic);
                    System.out.println("Qos: " + message.getQos());
                    System.out.println("message content: " + new String(message.getPayload()));

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });

            client.connect(options);
            client.subscribe("$share/cluster/mqtt/test", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
