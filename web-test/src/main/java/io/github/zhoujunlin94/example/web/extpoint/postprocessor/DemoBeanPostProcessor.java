package io.github.zhoujunlin94.example.web.extpoint.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024年01月19日 14:59
 * @desc
 */
@Slf4j
@Component
public class DemoBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 实例化->依赖注入->aware接口->前置操作->初始化
        if ("userService".equals(beanName)) {
            log.warn("003-DemoBeanPostProcessor.postProcessBeforeInitialization===>");
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 初始化之后
        if ("userService".equals(beanName)) {
            log.warn("004-DemoBeanPostProcessor.postProcessAfterInitialization===>");
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
