package io.github.zhoujunlin94.example.rabbitmq.consumer;

import io.github.zhoujunlin94.example.rabbitmq.message.Demo03Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 18:29
 * @desc
 */
@Slf4j
@Component
@RabbitListener(queues = Demo03Message.QUEUE_B)
public class Demo03ConsumerB {

    @RabbitHandler
    public void onMessage(Demo03Message message) {
        log.info("[Demo03ConsumerB#onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
