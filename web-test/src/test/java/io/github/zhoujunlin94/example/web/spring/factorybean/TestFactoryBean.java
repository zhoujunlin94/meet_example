package io.github.zhoujunlin94.example.web.spring.factorybean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhoujunlin
 * @date 2024/3/30 21:48
 * @desc a. 被FactoryBean创建的产品
 * 1. 会认为创建、依赖注入、Aware接口回调、前初始化这些都是FactoryBean的职责，这些流程都不会执行
 * 2. 唯有后初始化的流程会走，也就是bean可以被代理增强
 * 3. 单例的产品不会存储于BeanFactory的singletonObjects成员中，而是另一个factoryBeanObjectCache成员中
 * b. 按名字去获取时，拿到的是产品对象，名字前面加 & 获取的是工厂对象
 */
@ComponentScan
public class TestFactoryBean {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestFactoryBean.class);
        Bean1 bean1 = (Bean1) applicationContext.getBean("bean1");
        Bean1 bean2 = (Bean1) applicationContext.getBean("bean1");
        Bean1 bean3 = (Bean1) applicationContext.getBean("bean1");

        System.out.println(bean1);
        System.out.println(bean2);
        System.out.println(bean3);

        // getObjectType#getObjectType方法需要返回类型
        System.out.println(applicationContext.getBean(Bean1.class));

        // 获取Bean1FactoryBean的方式
        System.out.println(applicationContext.getBean(Bean1FactoryBean.class));
        System.out.println(applicationContext.getBean("&bean1"));

        applicationContext.close();
    }

}
