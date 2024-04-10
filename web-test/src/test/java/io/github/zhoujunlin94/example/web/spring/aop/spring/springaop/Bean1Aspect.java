package io.github.zhoujunlin94.example.web.spring.aop.spring.springaop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024/3/31 13:34
 * @desc
 */
@Component
@Aspect
public class Bean1Aspect {


    // 故意对Bean1中所有方法进行增强
    @Before("execution(* io.github.zhoujunlin94.example.web.spring.aop.spring.springaop.Bean100.*(..))")
    public void before() {
        System.out.println("Bean1Aspect.before...");
    }

}
