package io.github.zhoujunlin94.example.rabbitmq.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 18:15
 * @desc
 */
@Data
public class Demo04Message implements Serializable {

    /**
     * header交换机   不需要routingkey  通过交换机以及header匹配队列
     */
    public static final String QUEUE = "QUEUE_DEMO_04";

    public static final String EXCHANGE = "EXCHANGE_DEMO_04";

    public static final String HEADER_KEY = "color";
    public static final String HEADER_VALUE = "red";

    private Integer id;

}
