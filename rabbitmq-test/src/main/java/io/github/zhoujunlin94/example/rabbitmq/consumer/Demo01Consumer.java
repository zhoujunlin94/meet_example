package io.github.zhoujunlin94.example.rabbitmq.consumer;

import io.github.zhoujunlin94.example.rabbitmq.message.Demo01Message;
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
@RabbitListener(queues = Demo01Message.QUEUE)
public class Demo01Consumer {

    @RabbitHandler
    public void onMessage1(Demo01Message message) {
        log.info("[Demo01Consumer#onMessage1][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

    /**
     * Message中可以获取更多的内容RoutingKey、创建时间等等信息
     * 测试 asyncRabbitTemplate.sendAndReceive使用Message
     *
     * @param message
     */
    /*@RabbitHandler(isDefault = true)
    public void onMessage2(org.springframework.amqp.core.Message message) {
        log.info("[Demo01Consumer#onMessage2][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }*/

}
