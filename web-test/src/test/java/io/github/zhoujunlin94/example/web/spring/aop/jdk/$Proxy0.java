package io.github.zhoujunlin94.example.web.spring.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author zhoujunlin
 * @date 2024/3/3 15:50
 * @desc JDK动态代理的源码  asm技术生成的  没有源码过程  直接生成字节码
 */
public class $Proxy0 extends Proxy implements JDKProxyDemo0.Foo {

    public $Proxy0(InvocationHandler h) {
        super(h);
    }

    static Method foo;
    static Method bar;

    static {
        try {
            foo = JDKProxyDemo0.Foo.class.getMethod("foo");
            bar = JDKProxyDemo0.Foo.class.getMethod("bar");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    @Override
    public void foo() {
        try {
            h.invoke(this, foo, new Object[0]);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public int bar() {
        try {
            return (int) h.invoke(this, bar, new Object[0]);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
