package io.github.zhoujunlin94.example.web.spring.aop.spring;

import lombok.SneakyThrows;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.Order;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024/3/4 22:33
 * @desc
 */
public class TestSpringAspect {

    @SneakyThrows
    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean("aspect1", Aspect1.class);
        applicationContext.registerBean(Config.class);
        applicationContext.registerBean(ConfigurationClassPostProcessor.class);
        /**
         *  BeanPostProcessor:  找到所有切面  如果是高级切面需要转换为低级切面  最后生成代理对象
         *  初始化后执行
         */
        applicationContext.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
        applicationContext.refresh();
//        printBeanDefinitionName(applicationContext);

        /**
         * 内部工作的重要方法
         * findEligibleAdvisors：收集适用指定bean的所有切面
         */
        AnnotationAwareAspectJAutoProxyCreator proxyCreator = applicationContext.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        // 找到所有有资格的切面  高级切面转换为低级切面
        Method findEligibleAdvisorsMethod = AbstractAdvisorAutoProxyCreator.class.getDeclaredMethod("findEligibleAdvisors", Class.class, String.class);
        findEligibleAdvisorsMethod.setAccessible(true);
        // 收集所有适用Target1的切面
        List<Advisor> advisors = (List<Advisor>) findEligibleAdvisorsMethod.invoke(proxyCreator, Target1.class, "target1");
        for (Advisor advisor : advisors) {
            // 除了spring内置的ExposeInvocationInterceptor  还有三个切面  高级切面转换为2个低级切面+1个低级切面
            System.out.println("advisor = " + advisor);
        }

        Method wrapIfNecessaryMethod = AbstractAutoProxyCreator.class.getDeclaredMethod("wrapIfNecessary", Object.class, String.class, Object.class);
        wrapIfNecessaryMethod.setAccessible(true);
        // spring使用容器中取  这里直接实例化测试
        Object target1 = wrapIfNecessaryMethod.invoke(proxyCreator, new Target1(), "target1", "target1");
        Object target2 = wrapIfNecessaryMethod.invoke(proxyCreator, new Target2(), "target2", "target2");
        // 匹配到切点表达式  生成cglib代理
        System.out.println("target1.getClass() = " + target1.getClass());
        // 未匹配到  原对象
        System.out.println("target2.getClass() = " + target2.getClass());

        ((Target1) target1).foo();

    }


    @Aspect  // 高级切面
    @Order(2)
    static class Aspect1 {

        @Order(2)  // 通知上添加顺序注解无效
        @Before("execution(* foo())")
        public void before() {
            System.out.println("Aspect1.before");
        }

        @Order(1)
        @Before("execution(* foo())")
        public void before2() {
            System.out.println("Aspect1.before2");
        }

        @After("execution(* foo())")
        public void after() {
            System.out.println("Aspect1.after");
        }
    }

    @Configuration
    static class Config {

        @Bean
        public MethodInterceptor advice1() {
            return new MethodInterceptor() {
                @Nullable
                @Override
                public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                    System.out.println("advisor1.invoke before...");
                    Object ret = invocation.proceed();
                    System.out.println("advisor1.invoke after...");
                    return ret;
                }
            };
        }

        @Bean  // 低级切面
        //@Order(1)  这里添加@Order无效
        public Advisor advisor1(Advice advice1) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice1);
            advisor.setOrder(3);
            return advisor;
        }
    }

    static class Target1 {
        public void foo() {
            System.out.println("Target1.foo");
        }
    }

    static class Target2 {
        public void bar() {
            System.out.println("Target2.bar");
        }
    }

    static void printBeanDefinitionName(ApplicationContext applicationContext) {
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
    }

}
