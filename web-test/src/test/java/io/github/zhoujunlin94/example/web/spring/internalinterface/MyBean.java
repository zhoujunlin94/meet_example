package io.github.zhoujunlin94.example.web.spring.internalinterface;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zhoujunlin
 * @date 2024/2/27 20:36
 * @desc
 */
public class MyBean implements BeanNameAware, ApplicationContextAware, BeanFactoryAware, InitializingBean, DisposableBean {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.err.println("当前bean:" + this + ",beanFactory是" + beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        System.err.println("当前bean:" + this + ",beanName是" + name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println("当前bean:" + this + ",初始化");
    }

    @PostConstruct
    public void init() {
        System.err.println("当前bean:" + this + ",@PostConstruct初始化");
    }

    @Override
    public void destroy() throws Exception {
        System.err.println("当前bean:" + this + ",销毁");
    }

    @PreDestroy
    public void b() {
        System.err.println("当前bean:" + this + ",@PreDestroy销毁");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.err.println("当前bean:" + this + ",applicationContext是" + applicationContext);
    }

    @Autowired
    public void a(ApplicationContext applicationContext) {
        System.err.println("当前bean:" + this + ",通过@Autowired获得的applicationContext是" + applicationContext);
    }


}
