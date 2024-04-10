package io.github.zhoujunlin94.example.web.spring.factorybean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024/3/30 22:13
 * @desc
 */
@Slf4j
@Component
public class Bean1PostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 初始化前操作也不会执行
        if ("bean1".equals(beanName) && bean instanceof Bean1) {
            log.warn("before [{}] init", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 初始化后操作会执行
        if ("bean1".equals(beanName) && bean instanceof Bean1) {
            log.warn("after [{}] init", beanName);
        }
        return bean;
    }
}
