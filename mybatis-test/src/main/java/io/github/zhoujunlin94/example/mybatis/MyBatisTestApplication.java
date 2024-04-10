package io.github.zhoujunlin94.example.mybatis;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 20:37
 * @desc
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication(exclude = {MapperAutoConfiguration.class, MybatisAutoConfiguration.class})
public class MyBatisTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisTestApplication.class, args);
    }

}
