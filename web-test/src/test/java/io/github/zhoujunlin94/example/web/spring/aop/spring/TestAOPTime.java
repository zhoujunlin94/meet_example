package io.github.zhoujunlin94.example.web.spring.aop.spring;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;

/**
 * @author zhoujunlin
 * @date 2024年03月10日 13:26
 * @desc 代理的创建时机
 * <p>
 * 实例化 -> (*) -> 依赖注入 -> 初始化 -> (*)
 * 代理的创建时机：
 * 1. 初始化之后：没有循环依赖
 * 2. 实例化后，依赖注入之前（有循环依赖） 暂存于二级缓存
 * <p>
 * 依赖注入和初始化不应该被增强，即还是原对象调用
 */
public class TestAOPTime {

    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean(Config.class);
        // 处理注解bean
        applicationContext.registerBean(ConfigurationClassPostProcessor.class);
        // 处理注解生成代理
        applicationContext.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
        // 处理依赖注入
        applicationContext.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 处理初始化@PostConstruct
        applicationContext.registerBean(CommonAnnotationBeanPostProcessor.class);
        applicationContext.refresh();

        Bean1 bean = applicationContext.getBean(Bean1.class);
        bean.foo();

    }


    @Configuration
    static class Config {

        @Bean
        public MethodInterceptor fooAdvice() {
            return new MethodInterceptor() {
                @Nullable
                @Override
                public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                    System.out.println("before...");
                    invocation.proceed();
                    System.out.println("after...");
                    return null;
                }
            };
        }

        @Bean
        public Advisor fooAdvisor(Advice fooAdvice) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            return new DefaultPointcutAdvisor(pointcut, fooAdvice);
        }


        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }


    }

    static class Bean1 {

        public Bean1() {
            System.out.println(this.getClass() + " invoke Bean1.Bean1()");
        }

        @Autowired  // 验证循环依赖
        public void setBean2(Bean2 bean2) {
            System.out.println(this.getClass() + "Bean1.setBean2()->" + bean2.getClass());
        }

        @PostConstruct
        public void init() {
            System.out.println(this.getClass() + " Bean1.init()");
        }

        public void foo() {
            System.out.println("Bean1.foo");
        }

    }

    static class Bean2 {

        public Bean2() {
            System.out.println(this.getClass() + " invoke Bean2.Bean2()");
        }

        @Autowired
        public void setBean1(Bean1 bean1) {
            // bean1是代理对象
            System.out.println(this.getClass() + " Bean2.setBean1()-> " + bean1.getClass());
        }

        @PostConstruct
        public void init() {
            System.out.println(this.getClass() + " Bean2.init()");
        }

    }

}
