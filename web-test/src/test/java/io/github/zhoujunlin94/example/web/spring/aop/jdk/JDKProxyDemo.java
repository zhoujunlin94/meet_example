package io.github.zhoujunlin94.example.web.spring.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhoujunlin
 * @date 2024/3/3 15:04
 * @desc
 */
public class JDKProxyDemo {

    interface Foo {
        void foo();
    }

    /**
     * jdk代理中  被代理的目标对象可以是final的
     */
    static final class Target implements Foo {

        @Override
        public void foo() {
            System.out.println("Target.foo");
        }

    }


    public static void main(String[] args) {
        // 被代理的目标对象
        Foo target = new Target();
        /**
         * JDK代理只能针对接口类型
         * proxyFoo是动态生成的对象
         * 代理对象与目标对象都实现了接口类 是同级的兄弟关系
         */
        Foo proxyFoo = (Foo) Proxy.newProxyInstance(JDKProxyDemo.class.getClassLoader(), new Class[]{Foo.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before.....");
                Object invokeRet = method.invoke(target, args);
                System.out.println("after.....");
                return invokeRet;
            }
        });

        proxyFoo.foo();

    }

}
