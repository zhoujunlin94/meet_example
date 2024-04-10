package io.github.zhoujunlin94.example.web.spring.factorybean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author zhoujunlin
 * @date 2024/3/30 21:48
 * @desc 如果存在当前bean的FactoryBean, spring认为诸如依赖注入、初始化、Aware等这些生命阶段都应该有FactoryBean种处理
 * 所以即使这里配置了都不会生效
 */
@Slf4j
public class Bean1 implements BeanFactoryAware {

    private Bean2 bean2;

    @Autowired
    public void setBean2(Bean2 bean2) {
        log.warn("setBean2({})", bean2);
        this.bean2 = bean2;
    }

    public Bean2 getBean2() {
        return bean2;
    }

    @PostConstruct
    public void init() {
        log.warn("init");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.warn("setBeanFactory({})", beanFactory);
    }
}
