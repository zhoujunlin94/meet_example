package io.github.zhoujunlin94.example.web.spring.aop.cglib;

import org.springframework.cglib.core.Signature;

/**
 * @author zhoujunlin
 * @date 2024/3/3 21:17
 * @desc MethodProxy是如何避免反射的？？？
 */
public class TargetFastClass /*extends FastClass*/ {

    static Signature s0 = new Signature("save", "()V");
    static Signature s1 = new Signature("save", "(I)V");
    static Signature s2 = new Signature("save", "(J)V");

    /**
     * 定义【目标对象】方法的序号  并根据签名返回对应的序号
     *
     * @param signature
     * @return
     */
    public int getIndex(Signature signature) {
        if (s0.equals(signature)) {
            return 0;
        } else if (s1.equals(signature)) {
            return 1;
        } else if (s2.equals(signature)) {
            return 2;
        }
        // 未找到
        return -1;
    }

    /**
     * 根据方法编号  正常调用方法（非反射  但需要目标对象自己调用）
     *
     * @param index
     * @param target 目标对象
     * @param args
     * @return
     */
    public Object invoke(int index, Object target, Object[] args) {
        if (index == 0) {
            ((Target) target).save();
            return null;
        } else if (index == 1) {
            ((Target) target).save((int) args[0]);
            return null;
        } else if (index == 2) {
            ((Target) target).save(((long) args[0]));
            return null;
        }
        throw new RuntimeException("无此方法");
    }

    public static void main(String[] args) {
        Target target = new Target();
        // MethodProxy.create(Target.class, Proxy.class, "()V", "save", "saveSuper");
        TargetFastClass fastClass = new TargetFastClass();
        int index = fastClass.getIndex(new Signature("save", "()V"));
        // Object invokeRet = methodProxy.invoke(target, args);
        fastClass.invoke(index, target, new Object[0]);

        index = fastClass.getIndex(new Signature("save", "(I)V"));
        fastClass.invoke(index, target, new Object[]{1});

        index = fastClass.getIndex(new Signature("save", "(J)V"));
        fastClass.invoke(index, target, new Object[]{2L});

    }


}
