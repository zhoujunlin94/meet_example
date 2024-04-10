package io.github.zhoujunlin94.example.rabbitmq.producer;

import io.github.zhoujunlin94.example.rabbitmq.message.Demo02Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 20:59
 * @desc
 */
@Component
public class Demo02Producer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id, String routingKey) {
        // 创建 Demo02Message 消息
        Demo02Message message = new Demo02Message();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo02Message.EXCHANGE, routingKey, message);
    }

}
