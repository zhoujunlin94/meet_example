package io.github.zhoujunlin94.example.seata.ds;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

/**
 * @author zhoujunlin
 * @date 2023年09月21日 22:19
 * @desc
 */
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@SpringBootApplication(exclude = {MapperAutoConfiguration.class, MybatisAutoConfiguration.class})
public class SeataMultipleDSApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataMultipleDSApplication.class);
    }

}
