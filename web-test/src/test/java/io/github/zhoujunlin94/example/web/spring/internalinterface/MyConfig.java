package io.github.zhoujunlin94.example.web.spring.internalinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author zhoujunlin
 * @date 2024/2/27 20:48
 * @desc
 */
@Configuration
public class MyConfig {

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        System.out.println("MyConfig.setApplicationContext:" + applicationContext);
    }

    @PostConstruct
    public void init() {
        System.out.println("init");
    }

    @Bean
    public BeanFactoryPostProcessor postProcessor1() {
        return beanFactory -> {
            System.out.println("MyConfig.postProcessor1");
        };
    }

}
