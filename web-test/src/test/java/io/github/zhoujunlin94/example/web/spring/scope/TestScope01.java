package io.github.zhoujunlin94.example.web.spring.scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

/**
 * @author zhoujunlin
 * @date 2024年02月29日 19:18
 * @desc scope作用域
 * prototype
 * singleton
 * ---- web ---
 * request
 * session
 * application
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MapperAutoConfiguration.class})
public class TestScope01 {

    public static void main(String[] args) {
        SpringApplication.run(TestScope01.class, args);
    }

}
