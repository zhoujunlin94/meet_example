package io.github.zhoujunlin94.example.web.extpoint.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024年01月19日 14:56
 * @desc
 */
@Slf4j
@Component
public class DemoBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 修改BeanDefinition信息
        //beanFactory.getBeanDefinition("").setAutowireCandidate(true);
        log.warn("DemoBeanFactoryPostProcessor===========>");
    }
}
