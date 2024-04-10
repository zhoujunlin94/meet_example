package io.github.zhoujunlin94.example.web.spring.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author zhoujunlin
 * @date 2024/3/31 22:09
 * @desc
 */
@Configuration
public class TestEventMulticaster {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestEventMulticaster.class);

        MyService myService = applicationContext.getBean(MyService.class);
        myService.doBusiness();

        applicationContext.close();
    }

    @Slf4j
    @Component
    static class MyService {

        @Autowired
        private ApplicationEventPublisher applicationEventPublisher;

        public void doBusiness() {
            log.warn("主线业务");
            applicationEventPublisher.publishEvent(new MyEvent("MyService#doBusiness"));
        }
    }

    @Slf4j
    @Component
    static class SmsApplicationListener implements ApplicationListener<MyEvent> {
        @Override
        public void onApplicationEvent(MyEvent event) {
            log.warn("发送短信");
        }
    }

    @Slf4j
    @Component
    static class EmailApplicationListener implements ApplicationListener<MyEvent> {
        @Override
        public void onApplicationEvent(MyEvent event) {
            log.warn("发送邮件");
        }
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
    public ApplicationEventMulticaster applicationEventMulticaster(AnnotationConfigApplicationContext applicationContext, ThreadPoolTaskExecutor executor) {
        // 配置自定义的事件发送器
        return new MyAbstractEventMulticaster() {

            //private List<ApplicationListener> applicationListenerList = new ArrayList<>();

            // GenericApplicationListener具备判断事件类型的能力
            private List<GenericApplicationListener> applicationListenerList = new ArrayList<>();

            // 收集事件监听器
            @Override
            public void addApplicationListenerBean(String listenerBeanName) {
                ApplicationListener applicationListener = applicationContext.getBean(listenerBeanName, ApplicationListener.class);
                ResolvableType interfaceEventType = ResolvableType.forClass(applicationListener.getClass()).getInterfaces()[0].getGeneric();
                System.out.println("当前事件监听器为：" + applicationListener + ", 能够处理的事件类型为：" + interfaceEventType);

                // 对事件进行包装  添加事件类型判断
                GenericApplicationListener genericApplicationListener = new GenericApplicationListener() {
                    @Override
                    public boolean supportsEventType(ResolvableType eventType) {
                        // 是否支持某时间类型     eventType为当前的事件类型
                        return interfaceEventType.isAssignableFrom(eventType);
                    }

                    @Override
                    public void onApplicationEvent(ApplicationEvent event) {
                        // 支持异步
                        executor.submit(() -> applicationListener.onApplicationEvent(event));
                    }
                };

                applicationListenerList.add(genericApplicationListener);
            }

            // 发布事件
            @Override
            public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
                for (GenericApplicationListener applicationListener : applicationListenerList) {
                    if (applicationListener.supportsEventType(ResolvableType.forClass(event.getClass()))) {
                        applicationListener.onApplicationEvent(event);
                    }
                }
            }
        };
    }

    static class MyEvent extends ApplicationEvent {
        public MyEvent(Object source) {
            super(source);
        }
    }

    static class MyAbstractEventMulticaster implements ApplicationEventMulticaster {
        @Override
        public void addApplicationListener(ApplicationListener<?> listener) {

        }

        @Override
        public void addApplicationListenerBean(String listenerBeanName) {

        }

        @Override
        public void removeApplicationListener(ApplicationListener<?> listener) {

        }

        @Override
        public void removeApplicationListenerBean(String listenerBeanName) {

        }

        @Override
        public void removeApplicationListeners(Predicate<ApplicationListener<?>> predicate) {

        }

        @Override
        public void removeApplicationListenerBeans(Predicate<String> predicate) {

        }

        @Override
        public void removeAllListeners() {

        }

        @Override
        public void multicastEvent(ApplicationEvent event) {

        }

        @Override
        public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {

        }
    }

}
