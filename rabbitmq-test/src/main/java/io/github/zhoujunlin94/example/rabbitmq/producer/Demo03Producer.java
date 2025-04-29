package io.github.zhoujunlin94.example.rabbitmq.producer;

import io.github.zhoujunlin94.example.rabbitmq.message.Demo03Message;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 20:59
 * @desc
 */
@Component
public class Demo03Producer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo03Message 消息
        Demo03Message message = new Demo03Message();
        message.setId(id);
        // 同步发送消息  fanout 不需要routingkey
        rabbitTemplate.convertAndSend(Demo03Message.EXCHANGE, null, message);
    }

}
