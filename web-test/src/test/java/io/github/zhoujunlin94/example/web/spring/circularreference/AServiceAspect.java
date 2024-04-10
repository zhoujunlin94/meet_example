package io.github.zhoujunlin94.example.web.spring.circularreference;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024年03月25日 16:36
 * @desc
 */
@Component
@Aspect
public class AServiceAspect {


    @Around("execution(* io.github.zhoujunlin94.example.web.spring.circularreference.AService.foo())")
    public Object fooAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("before...");
        Object ret = pjp.proceed();
        System.out.println("after...");
        return ret;
    }

}
