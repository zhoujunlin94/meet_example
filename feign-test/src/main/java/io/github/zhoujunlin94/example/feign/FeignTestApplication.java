package io.github.zhoujunlin94.example.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhoujunlin
 * @date 2023年03月28日 22:29
 * @desc
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "io.github.zhoujunlin94.example.feign.client")
public class FeignTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignTestApplication.class, args);
    }

}
