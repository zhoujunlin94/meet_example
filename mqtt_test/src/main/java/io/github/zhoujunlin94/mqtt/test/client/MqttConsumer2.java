package io.github.zhoujunlin94.mqtt.test.client;

import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author zhoujunlin
 * @date 2024/8/1 21:34
 */
public class MqttConsumer2 {


    @SneakyThrows
    public static void main(String[] args) {
        try {
            MqttClient client = new MqttClient("tcp://broker.emqx.io:1883", MqttClient.generateClientId(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setKeepAliveInterval(60);
            options.setConnectionTimeout(60);
            options.setAutomaticReconnect(true);
            // 设置回调
            client.setCallback(new MqttClientCallback());

            client.connect(options);
//            client.subscribe("$share/cluster/mqtt/demo2", 0);
            client.subscribe("mqtt/demo2", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
