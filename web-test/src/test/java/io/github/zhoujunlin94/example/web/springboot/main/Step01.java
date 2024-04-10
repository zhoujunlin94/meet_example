package io.github.zhoujunlin94.example.web.springboot.main;

import lombok.SneakyThrows;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024年03月28日 10:26
 * @desc
 */
public class Step01 {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        // 添加事件监听器  用于监听到事件回调处理逻辑
        springApplication.addListeners(event -> {
            System.out.println("事件来源: " + event.getClass());
        });

        // 加载配置文件中(spring.factories)的SpringApplicationRunListener
        List<String> appRunListenerNames = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, Step01.class.getClassLoader());
        for (String appRunListenerName : appRunListenerNames) {
            // 目前只有一个EventPublishingRunListener
            System.out.println("publisherClass: =>" + appRunListenerName);
            // 生成对象
            Class<?> appRunListenerClazz = Class.forName(appRunListenerName);
            Constructor<?> constructor = appRunListenerClazz.getConstructor(SpringApplication.class, String[].class);
            SpringApplicationRunListener publisher = (SpringApplicationRunListener) constructor.newInstance(springApplication, args);

            // 开始发布事件
            DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();
            publisher.starting(bootstrapContext); // springboot开始启动事件  ApplicationStartingEvent
            publisher.environmentPrepared(bootstrapContext, new StandardEnvironment());  // 环境信息准备完毕  ApplicationEnvironmentPreparedEvent
            GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
            publisher.contextPrepared(genericApplicationContext);  // 在spring容器创建并调用初始化器之后  发送此事件  ApplicationContextInitializedEvent
            publisher.contextLoaded(genericApplicationContext);    //  所有BeanDefinition加载完成  ApplicationPreparedEvent
            genericApplicationContext.refresh();
            publisher.started(genericApplicationContext, Duration.ofSeconds(10));   // 容器初始化完成后  refresh调用完成  ApplicationStartedEvent
            publisher.ready(genericApplicationContext, Duration.ofSeconds(11));  // springboot启动完毕    ApplicationReadyEvent

            publisher.failed(genericApplicationContext, new Exception("故意抛出的异常"));  // springboot启动出错
        }

    }

}
