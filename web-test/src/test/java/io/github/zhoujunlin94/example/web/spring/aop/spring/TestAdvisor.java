package io.github.zhoujunlin94.example.web.spring.aop.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author zhoujunlin
 * @date 2024/3/3 22:13
 * @desc
 */
public class TestAdvisor {

    public static void main(String[] args) {
        // 1. 定义切入点  表达式切入点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())");

        // 2. 定义通知
        MethodInterceptor advice = new MethodInterceptor() {
            @Nullable
            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                System.out.println("before...");
                // 调用目标方法
                Object ret = invocation.proceed();
                System.out.println("after...");
                return ret;
            }
        };

        // 3. 定义切面    封装切入点与通知
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

        // 4. 创建代理
        Target1 target = new Target1();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.setInterfaces(target.getClass().getInterfaces());
        // 添加切面
        proxyFactory.addAdvisor(advisor);

        /**
         * 生成代理的方式是jdk还是cglib  由spring内部决定
         * 规则如下：
         * a.  proxyTargetClass为false (ProxyConfigs属性 proxyFactory.setProxyTargetClass(false))
         *     目标对象实现了接口 (proxyFactory.setInterfaces(target.getClass().getInterfaces());)   【使用JDK】
         * b. proxyTargetClass为false 目标对象没有实现接口   【使用cglib】
         * c. proxyTargetClass为true   【使用cglib】    @EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
         */

        Target1 proxy = (Target1) proxyFactory.getProxy();
        /**
         * JDK:
         * class io.github.zhoujunlin94.example.web.spring.aop.spring.$Proxy2
         * CGLIB:
         * class io.github.zhoujunlin94.example.web.spring.aop.spring.Target2$$EnhancerBySpringCGLIB$$818f8618
         */
        System.out.println("proxyClass = " + proxy.getClass());
        proxy.foo();
        proxy.bar();

    }

}

interface I1 {
    void foo();

    void bar();
}

class Target1 implements I1 {
    @Override
    public void foo() {
        System.out.println("Target1.foo");
    }

    @Override
    public void bar() {
        System.out.println("Target1.bar");
    }
}

class Target2 {
    public void foo() {
        System.out.println("Target2.foo");
    }

    public void bar() {
        System.out.println("Target2.bar");
    }
}