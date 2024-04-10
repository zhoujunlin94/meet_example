package io.github.zhoujunlin94.example.web.spring.aop.spring;

import cn.hutool.core.util.ReflectUtil;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024年03月10日 15:00
 * @desc
 */
public class TestConvertAroundAdvice {

    static class Aspect {

        @Before("execution(* foo())")
        public void before() {
            System.out.println("Aspect.before");
        }

        @Before("execution(* foo())")
        public void before2() {
            System.out.println("Aspect.before2");
        }

        public void after() {
            System.out.println("Aspect.after");
        }

        @AfterReturning("execution(* foo())")
        public void afterReturning() {
            System.out.println("Aspect.afterReturning");
        }

        @AfterThrowing("execution(* foo())")
        public void afterThrowing() {
            System.out.println("Aspect.afterThrowing");
        }

        public void around() {
            System.out.println("Aspect.around");
        }

    }

    static class Target {
        public void foo() {
            System.out.println("Target.foo");
        }
    }


    @SneakyThrows
    public static void main(String[] args) {
        // 切面实例工厂
        AspectInstanceFactory aif = new SingletonAspectInstanceFactory(new Aspect());
        // 切面
        List<Advisor> advisorList = new ArrayList<>();
        for (Method method : Aspect.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                // 解析切入点
                String expression = method.getAnnotation(Before.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 前置通知
                AspectJMethodBeforeAdvice beforeAdvice = new AspectJMethodBeforeAdvice(method, pointcut, aif);
                //AspectJAroundAdvice      环绕
                //AspectJAfterReturningAdvice   返回
                //AspectJAfterThrowingAdvice   异常
                //AspectJAfterAdvice   后置
                // 切面
                advisorList.add(new DefaultPointcutAdvisor(pointcut, beforeAdvice));
            } else if (method.isAnnotationPresent(AfterReturning.class)) {
                // 解析切入点
                String expression = method.getAnnotation(AfterReturning.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // return通知
                AspectJAfterReturningAdvice afterReturningAdvice = new AspectJAfterReturningAdvice(method, pointcut, aif);
                // 切面
                advisorList.add(new DefaultPointcutAdvisor(pointcut, afterReturningAdvice));
            } else if (method.isAnnotationPresent(AfterThrowing.class)) {
                // 解析切入点
                String expression = method.getAnnotation(AfterThrowing.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                // 异常通知
                AspectJAfterThrowingAdvice afterThrowingAdvice = new AspectJAfterThrowingAdvice(method, pointcut, aif);
                // 切面
                advisorList.add(new DefaultPointcutAdvisor(pointcut, afterThrowingAdvice));
            }
        }
        // 转换前
        advisorList.forEach(System.out::println);

        // 这里会有一个排序操作

        // 非环绕通知转换为环绕通知
        Target target = new Target();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        // 将ExposeInvocationInterceptor放入第一个通知  将MethodInvocation放入线程上下文
        proxyFactory.addAdvice(ExposeInvocationInterceptor.INSTANCE);
        proxyFactory.addAdvisors(advisorList);

        System.out.println("============>");
        // 转换为环绕通知  适配器模式
        Method fooMethod = Target.class.getMethod("foo");
        List<Object> dynamicInterceptionAdvice = proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(fooMethod, Target.class);
        dynamicInterceptionAdvice.forEach(System.out::println);

        // 创建调用链 （执行所有环绕通知和目标方法）
        Constructor<ReflectiveMethodInvocation> reflectiveMethodInvocationConstructor = ReflectUtil.getConstructor(ReflectiveMethodInvocation.class, Object.class, Object.class, Method.class, Object[].class,
                Class.class, List.class);
        reflectiveMethodInvocationConstructor.setAccessible(true);
        ReflectiveMethodInvocation reflectiveMethodInvocation = reflectiveMethodInvocationConstructor.newInstance(
                null, target, fooMethod, new Object[0], Target.class, dynamicInterceptionAdvice
        );
        /**
         *  会报错：因为其它通知方法里面可能也需要MethodInvocation对象
         *  所以需要使用ExposeInvocationInterceptor将MethodInvocation放到线程上下文
         */
        reflectiveMethodInvocation.proceed();

    }


}
