package io.github.zhoujunlin94.example.web.spring.aop.spring;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * @author zhoujunlin
 * @date 2024/3/4 22:07
 * @desc
 */
public class TestPointcutMatch {

    public static void main(String[] args) throws NoSuchMethodException {
        AspectJExpressionPointcut pt = new AspectJExpressionPointcut();
        // 匹配方法
        pt.setExpression("execution(* foo())");
        System.out.println("匹配方法：" + pt.matches(T1.class.getMethod("foo"), T1.class));  // true
        System.out.println("匹配方法：" + pt.matches(T1.class.getMethod("bar"), T1.class));   // false

        pt = new AspectJExpressionPointcut();
        // 匹配注解
        pt.setExpression("@annotation(org.springframework.transaction.annotation.Transactional)");
        System.out.println("匹配方法注解：" + pt.matches(T1.class.getMethod("foo"), T1.class));  // true
        System.out.println("匹配方法注解：" + pt.matches(T1.class.getMethod("bar"), T1.class));   // false

        /**
         * 那spring底层是不是就是使用AspectJExpressionPointcut在匹配事务注解的呢？
         * 答案：不是的
         * 因为事务注解不仅可以放在方法上，还可以放在类上，接口上   表示类/接口实现类里的所有方法都包含事务
         * 而AspectJExpressionPointcut只能匹配方法
         * spring底层是使用StaticMethodMatcherPointcut匹配的
         */

        StaticMethodMatcherPointcut transPointcut = new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                // 先判断方法上是否包含事务注解
                MergedAnnotations mergedAnnotations = MergedAnnotations.from(method);
                if (mergedAnnotations.isPresent(Transactional.class)) {
                    return true;
                }
                // 默认只会找当前类的注解   不会查找这个类继承树上其他父类是否存在注解
//                mergedAnnotations = MergedAnnotations.from(targetClass);
                mergedAnnotations = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if (mergedAnnotations.isPresent(Transactional.class)) {
                    return true;
                }
                return false;
            }
        };

        System.out.println("匹配方法/类注解：" + transPointcut.matches(T1.class.getMethod("foo"), T1.class));  // true
        System.out.println("匹配方法/类注解：" + transPointcut.matches(T1.class.getMethod("bar"), T1.class));   // false

        System.out.println("匹配方法/类注解：" + transPointcut.matches(T2.class.getMethod("foo"), T2.class));  // true
        System.out.println("匹配方法/类注解：" + transPointcut.matches(T2.class.getMethod("bar"), T2.class));   // true

        System.out.println("匹配方法/类注解：" + transPointcut.matches(T3.class.getMethod("foo"), T3.class));  // true
        System.out.println("匹配方法/类注解：" + transPointcut.matches(T3.class.getMethod("bar"), T3.class));   // true

    }

    static class T1 {

        @Transactional
        public void foo() {
        }

        public void bar() {
        }

    }

    @Transactional
    static class T2 {
        public void foo() {

        }

        public void bar() {

        }
    }

    @Transactional
    interface I1 {
        void foo();
    }

    static class T3 implements I1 {
        @Override
        public void foo() {

        }

        public void bar() {

        }
    }

}
