package io.github.zhoujunlin94.example.web.spring.aop.spring;

import cn.hutool.core.util.ReflectUtil;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024/3/11 21:40
 * @desc 有参数绑定的通知调用时还需要切入点，对参数进行匹配和绑定
 * 动态通知复杂度高，性能要比静态通知要低
 */
public class TestDynamicAdvice {

    static class Target {

        public void foo(int x) {
            System.out.println("Target.foo.int." + x);
        }

    }

    @org.aspectj.lang.annotation.Aspect
    static class Aspect {

        @Before("execution(* foo(..))")    // 静态通知调用  不需要参数绑定   执行时不需要切点
        public void before() {
            System.out.println("Aspect.before");
        }

        @Before("execution(* foo(..)) && args(x)")   // 动态通知调用  需要进行参数绑定  执行时需要切点对象
        public void before(int x) {
            System.out.println("Aspect.before.int." + x);
        }

    }

    @SneakyThrows
    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean(Aspect.class);
        applicationContext.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);
        applicationContext.refresh();


        AnnotationAwareAspectJAutoProxyCreator proxyCreator = applicationContext.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        Method findEligibleAdvisorsMethod = AbstractAdvisorAutoProxyCreator.class.getDeclaredMethod("findEligibleAdvisors", Class.class, String.class);
        findEligibleAdvisorsMethod.setAccessible(true);
        List<Advisor> advisors = (List<Advisor>) findEligibleAdvisorsMethod.invoke(proxyCreator, TestDynamicAdvice.Target.class, "target");

        Target target = new Target();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisors(advisors);

        // 获取代理
        Object proxy = proxyFactory.getProxy();
        Method fooMethod = Target.class.getMethod("foo", int.class);
        List<Object> methodInterceptors = proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(fooMethod, Target.class);
        for (Object methodInterceptor : methodInterceptors) {
            /**
             * ExposeInvocationInterceptor： 将调用链对象存入线程上下文
             * MethodBeforeAdviceInterceptor  前置通知（已转换环绕通知）
             * InterceptorAndDynamicMethodMatcher 动态通知对象  持有MethodInterceptor（环绕通知） + methodMatcher（切入点） 对象
             */
            System.out.println(methodInterceptor);
        }


        System.out.println("==============>");

        Constructor<ReflectiveMethodInvocation> reflectiveMethodInvocationConstructor = ReflectUtil.getConstructor(ReflectiveMethodInvocation.class, Object.class, Object.class, Method.class, Object[].class,
                Class.class, List.class);
        reflectiveMethodInvocationConstructor.setAccessible(true);
        ReflectiveMethodInvocation reflectiveMethodInvocation = reflectiveMethodInvocationConstructor.newInstance(
                proxy, target, fooMethod, new Object[]{100}, Target.class, methodInterceptors
        );
        reflectiveMethodInvocation.proceed();

    }

}
