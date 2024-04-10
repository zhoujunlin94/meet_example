package io.github.zhoujunlin94.example.rabbitmq.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 18:15
 * @desc 要实现 Java Serializable 序列化接口。因为 RabbitTemplate 默认使用 Java 自带的序列化方式，进行序列化 POJO 类型的消息。
 */
@Data
public class Demo01Message implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_01";

    public static final String EXCHANGE = "EXCHANGE_DEMO_01";

    public static final String ROUTING_KEY = "ROUTING_KEY_01";

    private Integer id;

}
