package io.github.zhoujunlin94.mqtt.test.client;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

/**
 * @author zhoujunlin
 * @date 2024/8/1 21:34
 */
public class MqttPublisher {


    @SneakyThrows
    public static void main(String[] args) {
        MqttClient client = new MqttClient("tcp://broker.emqx.io:1883", MqttClient.generateClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setKeepAliveInterval(60);
        options.setConnectionTimeout(60);
        options.setAutomaticReconnect(true);
        client.connect(options);

        JSONObject content = new JSONObject().fluentPut("msg", "every body here?").fluentPut("msgId", IdUtil.fastSimpleUUID());
        // 创建消息并设置 QoS
        MqttMessage message = new MqttMessage(content.toJSONString().getBytes(StandardCharsets.UTF_8));
        message.setQos(0);

        // 发布消息
        client.publish("mqtt/test", message);
        // 关闭连接
        client.disconnect();
        // 关闭客户端
        client.close();
    }


}
