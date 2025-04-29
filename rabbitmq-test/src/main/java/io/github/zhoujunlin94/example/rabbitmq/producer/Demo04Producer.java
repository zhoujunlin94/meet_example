package io.github.zhoujunlin94.example.rabbitmq.producer;

import io.github.zhoujunlin94.example.rabbitmq.message.Demo04Message;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 20:59
 * @desc
 */
@Component
public class Demo04Producer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id, String headerValue) {
        // 创建 MessageProperties 属性
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(Demo04Message.HEADER_KEY, headerValue); // 设置 header
        // 创建 Message 消息
        Message message = rabbitTemplate.getMessageConverter().toMessage(
                new Demo04Message().setId(id), messageProperties);
        // 同步发送消息
        rabbitTemplate.send(Demo04Message.EXCHANGE, null, message);
    }

}
