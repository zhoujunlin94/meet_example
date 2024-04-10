package io.github.zhoujunlin94.example.rabbitmq.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 18:15
 * @desc
 */
@Data
public class Demo03Message implements Serializable {

    /**
     * fanout交换机   绑定两个队列  不需要routingkey
     */
    public static final String QUEUE_A = "QUEUE_DEMO_03_A";
    public static final String QUEUE_B = "QUEUE_DEMO_03_B";

    public static final String EXCHANGE = "EXCHANGE_DEMO_03";

    private Integer id;

}
