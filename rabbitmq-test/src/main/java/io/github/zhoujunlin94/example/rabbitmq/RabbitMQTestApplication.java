package io.github.zhoujunlin94.example.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zhoujunlin
 * @date 2023年03月28日 17:10
 * @desc
 */
@EnableAsync
@SpringBootApplication
public class RabbitMQTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQTestApplication.class, args);
    }

}
