package io.github.zhoujunlin94.example.web.spring.aop.cglib;

import org.springframework.cglib.core.Signature;

/**
 * @author zhoujunlin
 * @date 2024/3/3 21:17
 * @desc MethodProxy是如何避免反射的？？？
 */
public class ProxyFastClass /*extends FastClass*/ {

    static Signature s0 = new Signature("saveSuper", "()V");
    static Signature s1 = new Signature("saveSuper", "(I)V");
    static Signature s2 = new Signature("saveSuper", "(J)V");

    /**
     * 定义【代理对象】方法的序号  并根据签名返回对应的序号
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
     * 根据方法编号  正常调用方法（非反射  但需要代理对象自己调用）
     *
     * @param index
     * @param proxy 代理对象
     * @param args
     * @return
     */
    public Object invoke(int index, Object proxy, Object[] args) {
        if (index == 0) {
            ((Proxy) proxy).saveSuper();
            return null;
        } else if (index == 1) {
            ((Proxy) proxy).saveSuper((int) args[0]);
            return null;
        } else if (index == 2) {
            ((Proxy) proxy).saveSuper(((long) args[0]));
            return null;
        }
        throw new RuntimeException("无此方法");
    }

    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        // MethodProxy.create(Target.class, Proxy.class, "()V", "save", "saveSuper");
        ProxyFastClass fastClass = new ProxyFastClass();
        int index = fastClass.getIndex(new Signature("saveSuper", "()V"));
        // Object invokeRet = methodProxy.invokeSuper(proxy, args);
        fastClass.invoke(index, proxy, new Object[0]);

        index = fastClass.getIndex(new Signature("saveSuper", "(I)V"));
        fastClass.invoke(index, proxy, new Object[]{1});

        index = fastClass.getIndex(new Signature("saveSuper", "(J)V"));
        fastClass.invoke(index, proxy, new Object[]{2L});

    }


}
