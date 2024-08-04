package io.github.zhoujunlin94.mqtt.test.client;

import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author zhoujunlin
 * @date 2024/8/1 21:34
 */
public class MqttConsumer {


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

            // $share/{GroupID}/{Topic}  通过mqtt的共享主题模式达到集群消费模式
            // 正常模式就是广播消费
            client.subscribe("mqtt/demo", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
