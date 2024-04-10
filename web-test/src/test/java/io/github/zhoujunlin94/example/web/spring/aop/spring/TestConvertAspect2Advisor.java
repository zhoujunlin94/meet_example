package io.github.zhoujunlin94.example.web.spring.aop.spring;

import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectInstanceFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import org.springframework.aop.aspectj.SingletonAspectInstanceFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024年03月10日 15:00
 * @desc
 */
public class TestConvertAspect2Advisor {

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

        public void afterReturning() {
            System.out.println("Aspect.afterReturning");
        }

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
            }
        }

        advisorList.forEach(System.out::println);
    }


}
