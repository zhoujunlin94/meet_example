package io.github.zhoujunlin94.example.web.spring.aop.spring;

import cn.hutool.core.collection.CollUtil;
import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024年03月10日 16:55
 * @desc 测试调用链：  递归+责任链
 */
public class TestMyMethodInvocation {

    static class Target {

        public void foo() {
            System.out.println("Target.foo");
        }

    }

    static class Advice1 implements MethodInterceptor {
        @Nullable
        @Override
        public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
            System.out.println("Advice1.invoke before...");
            // 调用下一个通知或者目标方法
            Object ret = invocation.proceed();
            System.out.println("Advice1.invoke after...");
            return ret;
        }
    }

    static class Advice2 implements MethodInterceptor {
        @Nullable
        @Override
        public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
            System.out.println("Advice2.invoke before...");
            Object ret = invocation.proceed();
            System.out.println("Advice2.invoke after...");
            return ret;
        }
    }

    static class MyMethodInvocation implements MethodInvocation {

        private Object target;  // 1
        private Method targetMethod;
        private Object[] targetArgs;
        private List<MethodInterceptor> methodInterceptorList; // 2
        private int callCount = 1;

        public MyMethodInvocation(Object target, Method method, Object[] args, List<MethodInterceptor> methodInterceptorList) {
            this.target = target;
            this.targetMethod = method;
            this.targetArgs = args;
            this.methodInterceptorList = methodInterceptorList;
        }

        @Nonnull
        @Override
        public Method getMethod() {
            return targetMethod;
        }

        @Nonnull
        @Override
        public Object[] getArguments() {
            return targetArgs;
        }

        @Nullable
        @Override
        public Object proceed() throws Throwable {
            // 如果还有环绕通知 调用环绕通知  没有的话  调用目标方法
            if (callCount > methodInterceptorList.size()) {
                return targetMethod.invoke(target, targetArgs);
            }
            // 逐个递归调用环绕通知  调用次数+1
            MethodInterceptor methodInterceptor = methodInterceptorList.get(callCount - 1);
            callCount++;
            return methodInterceptor.invoke(this);
        }

        @Nullable
        @Override
        public Object getThis() {
            return target;
        }

        @Nonnull
        @Override
        public AccessibleObject getStaticPart() {
            return targetMethod;
        }
    }


    @SneakyThrows
    public static void main(String[] args) {
        Target target = new Target();
        List<MethodInterceptor> adviceList = CollUtil.newArrayList(new Advice1(), new Advice2());
        MyMethodInvocation methodInvocation = new MyMethodInvocation(target, Target.class.getMethod("foo"), new Object[0], adviceList);
        methodInvocation.proceed();
    }

}
