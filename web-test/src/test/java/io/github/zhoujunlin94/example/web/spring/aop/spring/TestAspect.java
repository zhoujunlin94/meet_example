package io.github.zhoujunlin94.example.web.spring.aop.spring;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

/**
 * @author zhoujunlin
 * @date 2024/3/3 21:44
 * @desc 切面：切入点+通知
 * spring定义切面两种方式  aspect/advisor
 * 1. aspect可以定义多组通知和切入点，advisor只能定义一组通知和切入点
 * 2. aspect底层还是advisor
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class, MapperAutoConfiguration.class})
public class TestAspect {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestAspect.class, args);
        MyService myService = applicationContext.getBean(MyService.class);
        myService.foo();
    }

}



