package io.github.zhoujunlin94.example.web.spring.aop.spring;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024/3/3 21:59
 * @desc
 */
@Component   // 交由spring管理
@Aspect  // 切面
public class MyAspect {

    // 切入点  所有foo方法
    @Before("execution(* foo(..))")
    public void before() {
        // 通知
        System.out.println("前置通知");
    }

    @After("execution(* foo(..))")
    public void after() {
        // 通知
        System.out.println("后置通知");
    }

}