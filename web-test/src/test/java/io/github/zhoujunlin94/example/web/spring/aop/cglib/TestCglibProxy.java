package io.github.zhoujunlin94.example.web.spring.aop.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zhoujunlin
 * @date 2024/3/3 20:48
 * @desc
 */
public class TestCglibProxy {

    public static void main(String[] args) {
        Target target = new Target();

        Proxy proxy = new Proxy();
        proxy.setMethodInterceptor(new MethodInterceptor() {
            @Override
            public Object intercept(Object proxy, Method targetMethod, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("before...");
                // 反射执行
                //Object invokeRet = targetMethod.invoke(target, args);

                // 无反射  需要目标对象
                //Object invokeRet = methodProxy.invoke(target, args);

                // 无反射  需要代理对象
                Object invokeRet = methodProxy.invokeSuper(proxy, args);
                System.out.println("after...");
                return invokeRet;
            }
        });

        proxy.save();
        proxy.save(1);
        proxy.save(2L);

    }

}
