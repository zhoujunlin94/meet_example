package io.github.zhoujunlin94.example.web.spring.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhoujunlin
 * @date 2024/3/31 22:09
 * @desc
 */
@Configuration
public class TestEventListener {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestEventListener.class);

        //initApplicationListener(applicationContext);

        MyService myService = applicationContext.getBean(MyService.class);
        myService.doBusiness();

        applicationContext.close();
    }

    private static void initApplicationListener(AnnotationConfigApplicationContext applicationContext) {
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            // 遍历所有bean下面的所有方法
            Object bean = applicationContext.getBean(beanName);
            for (Method method : bean.getClass().getMethods()) {
                // 判断是否是MyEventListener
                if (method.isAnnotationPresent(MyEventListener.class)) {
                    // 将当前方法包装层一个ApplicationListener bean
                    ApplicationListener applicationListener = event -> {
                        System.out.println("当前事件：" + event);
                        // 获取方法上的事件类型 判断类型一致才可以处理
                        Class<?> eventType = method.getParameterTypes()[0];
                        if (eventType.isAssignableFrom(event.getClass())) {
                            try {
                                method.invoke(bean, event);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    // 将包装好的监听器  放入容器
                    applicationContext.addApplicationListener(applicationListener);
                }
            }
        }
    }

    @Bean
    public SmartInitializingSingleton myEventListeners(AnnotationConfigApplicationContext applicationContext) {
        // SmartInitializingSingleton类是在所有单例对象完成后执行
        return () -> {
            for (String beanName : applicationContext.getBeanDefinitionNames()) {
                Object bean = applicationContext.getBean(beanName);
                for (Method method : bean.getClass().getMethods()) {
                    if (method.isAnnotationPresent(MyEventListener.class)) {
                        ApplicationListener applicationListener = event -> {
                            Class<?> eventType = method.getParameterTypes()[0];
                            if (eventType.isAssignableFrom(event.getClass())) {
                                try {
                                    method.invoke(bean, event);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        applicationContext.addApplicationListener(applicationListener);
                    }
                }
            }
        };
    }

    @Slf4j
    @Component
    static class MyService {

        @Autowired
        private ApplicationEventPublisher applicationEventPublisher;

        public void doBusiness() {
            log.warn("主线业务");
            // 主线业务完成后  需要做一些支线业务
//            log.warn("发送短信");
//            log.warn("发送邮件");
            applicationEventPublisher.publishEvent(new MyEvent("MyService#doBusiness"));
        }
    }

    @Slf4j
    //@Component
    static class SmsApplicationListener implements ApplicationListener<MyEvent> {
        @Override
        public void onApplicationEvent(MyEvent event) {
            log.warn("发送短信");
        }
    }

    @Slf4j
    @Component
    static class SmsService {
        @MyEventListener
        public void listener(MyEvent myEvent) {
            log.warn("发送短信");
        }
    }

    @Slf4j
    //@Component
    static class EmailApplicationListener implements ApplicationListener<MyEvent> {
        @Override
        public void onApplicationEvent(MyEvent event) {
            log.warn("发送邮件");
        }
    }

    @Slf4j
    @Component
    static class EmailService {
        //@EventListener
        @MyEventListener
        public void listener(MyEvent myEvent) {
            log.warn("发送邮件");
        }
    }

    /**
     * 写一个自定义注解  来测试内部是怎么实现事件监听的
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyEventListener {
    }

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(3);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(100);
        return threadPoolTaskExecutor;
    }

    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster(ThreadPoolTaskExecutor executor) {
        // 配置异步的事件发送器
        SimpleApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        applicationEventMulticaster.setTaskExecutor(executor);
        return applicationEventMulticaster;
    }

    static class MyEvent extends ApplicationEvent {

        public MyEvent(Object source) {
            super(source);
        }

    }


}
