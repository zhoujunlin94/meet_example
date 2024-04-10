package io.github.zhoujunlin94.example.redis;

import io.github.zhoujunlin94.meet.redis.annotation.EnableRedisPubSub;
import io.github.zhoujunlin94.meet.redis.annotation.EnableRedisQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhoujunlin
 * @date 2023年03月26日 21:35
 * @desc
 */
@EnableRedisQueue
@EnableRedisPubSub
@SpringBootApplication
public class RedisTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisTestApplication.class, args);
    }

}
