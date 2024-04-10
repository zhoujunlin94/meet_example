package io.github.zhoujunlin94.example.web.spring.aop.cglib;

import lombok.Setter;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author zhoujunlin
 * @date 2024/3/3 20:43
 * @desc 模拟cglib代理源码
 */
public class Proxy extends Target {

    @Setter
    private MethodInterceptor methodInterceptor;

    static Method save;
    static Method savei;
    static Method savel;

    static MethodProxy saveMethodProxy;
    static MethodProxy saveiMethodProxy;
    static MethodProxy savelMethodProxy;

    static {
        try {
            save = Target.class.getMethod("save");
            savei = Target.class.getMethod("save", int.class);
            savel = Target.class.getMethod("save", long.class);

            saveMethodProxy = MethodProxy.create(Target.class, Proxy.class, "()V", "save", "saveSuper");
            saveiMethodProxy = MethodProxy.create(Target.class, Proxy.class, "(I)V", "save", "saveSuper");
            savelMethodProxy = MethodProxy.create(Target.class, Proxy.class, "(J)V", "save", "saveSuper");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    // ==================> 原始功能方法
    public void saveSuper() {
        super.save();
    }

    public void saveSuper(int i) {
        super.save(i);
    }

    public void saveSuper(long i) {
        super.save(i);
    }

    // ==================> 带有增强功能的方法
    @Override
    public void save() {
        try {
            methodInterceptor.intercept(this, save, new Object[0], saveMethodProxy);
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public void save(int i) {
        try {
            methodInterceptor.intercept(this, savei, new Object[]{i}, saveiMethodProxy);
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public void save(long i) {
        try {
            methodInterceptor.intercept(this, savel, new Object[]{i}, savelMethodProxy);
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
