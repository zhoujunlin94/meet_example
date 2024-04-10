package io.github.zhoujunlin94.example.web.spring.aop.jdk;

/**
 * @author zhoujunlin
 * @date 2024/3/3 15:04
 * @desc
 */
public class JDKProxyDemo0 {

    interface Foo {
        void foo();

        int bar();
    }

    /**
     * jdk代理中  被代理的目标对象可以是final的
     */
    static final class Target implements Foo {

        @Override
        public void foo() {
            System.out.println("Target.foo");
        }

        @Override
        public int bar() {
            System.out.println("Target.bar");
            return 100;
        }
    }


    public static void main(String[] args) {
        $Proxy0 $Proxy0 = new $Proxy0((proxy, method, args1) -> {
            System.out.println("before");
            Object invokeRet = method.invoke(new Target(), args);
            System.out.println("after");
            return invokeRet;
        });

        $Proxy0.foo();
        $Proxy0.bar();

    }

}
