package io.github.zhoujunlin94.example.web.spring.aop.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zhoujunlin
 * @date 2024/3/3 15:04
 * @desc
 */
public class CgilbProxyDemo {

    /**
     * cglib代理中  被代理的目标对象不可以是final的  方法也不能是final的
     * 因为代理对象是通过继承Target类实现的
     */
    static class Target {
        public void foo() {
            System.out.println("Target.foo");
        }
    }


    public static void main(String[] args) {

        //Target target = new Target();

        /**
         * 代理对象继承了目标对象  是父子关系
         */
        Target proxy = (Target) Enhancer.create(Target.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object proxy, Method targetMethod, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("before...");
                // 反射实现调用目标方法  效率不高
                //Object invokeRet = targetMethod.invoke(target, args);

                // 内部没有使用反射    但是需要有目标对象  spring选择了此方式
                // Object invokeRet = methodProxy.invoke(target, args);

                // 内部没有使用反射  且不需要有目标对象
                Object invokeRet = methodProxy.invokeSuper(proxy, args);

                System.out.println("after...");
                return invokeRet;
            }
        });

        proxy.foo();

    }

}
