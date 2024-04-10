package io.github.zhoujunlin94.example.web.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zhoujunlin
 * @date 2023年05月07日 21:29
 * @desc
 */
@Component
public class LifeCycleBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

    private String value;

    public LifeCycleBean() {
        System.err.println("LifeCycleBean实例化");
    }

    @Value("${JAVA_HOME}")
    public void setValue(String value) {
        System.err.println("LifeCycleBean依赖注入:" + value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.err.println("LifeCycleBean BeanFactoryAware, beanFactory:" + beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        System.err.println("LifeCycleBean BeanNameAware, beanName:" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.err.println("LifeCycleBean ApplicationContextAware, applicationContext:" + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println("LifeCycleBean afterPropertiesSet");
    }

    @PostConstruct
    public void init() {
        System.err.println("LifeCycleBean PostConstruct");
    }

    @PreDestroy
    public void destroy() {
        // 容器关闭的时候才会调用
        System.err.println("LifeCycleBean destroy");
    }

}
