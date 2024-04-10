package io.github.zhoujunlin94.example.rabbitmq.producer;

import com.alibaba.fastjson.JSONObject;
import io.github.zhoujunlin94.example.rabbitmq.message.Demo01Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 18:19
 * @desc
 */
@Slf4j
@Component
public class Demo01Producer {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private AsyncRabbitTemplate asyncRabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 同步发送消息  指定 Exchange + RoutingKey ，从而路由到一个 Queue 中
        // 交换机名 、  routing key名、  消息
        rabbitTemplate.convertAndSend(Demo01Message.EXCHANGE, Demo01Message.ROUTING_KEY, message);
    }

    public void syncSendDefault(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 同步发送消息
        // routing key名  消息
        // 这里将队列名传给了routing key
        // 默认交换器AMQP default，隐式地绑定到每个队列，路由键等于队列名称
        rabbitTemplate.convertAndSend(Demo01Message.QUEUE, message);
    }

    @Async
    public ListenableFuture<Void> asyncSend(Integer id) {
        // 使用springboot的一部发送
        try {
            // 发送消息
            this.syncSend(id);
            // 返回成功的 Future
            return AsyncResult.forValue(null);
        } catch (Throwable ex) {
            // 返回异常的 Future
            return AsyncResult.forExecutionException(ex);
        }
    }

    public AsyncRabbitTemplate.RabbitFuture<Message> asyncSend2(Integer id) {
        // 使用rabbitmq自身的异步api
        Demo01Message demo01Message = new Demo01Message();
        demo01Message.setId(id);
        Message message = MessageBuilder
                .withBody(JSONObject.toJSONBytes(demo01Message))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        return asyncRabbitTemplate.sendAndReceive(Demo01Message.EXCHANGE, Demo01Message.ROUTING_KEY, message);
    }

}
