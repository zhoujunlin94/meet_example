package io.github.zhoujunlin94.example.rabbitmq.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoujunlin
 * @date 2023年05月14日 18:15
 * @desc
 */
@Data
public class Demo02Message implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_02";

    public static final String EXCHANGE = "EXCHANGE_DEMO_02";

    /**
     * routing key 为一个句点号 "." 分隔的字符串。
     * "*" 用于匹配一个单词，"#" 用于匹配多个单词（可以是零个）
     */
    public static final String ROUTING_KEY = "#.yu.nai";

    private Integer id;

}
