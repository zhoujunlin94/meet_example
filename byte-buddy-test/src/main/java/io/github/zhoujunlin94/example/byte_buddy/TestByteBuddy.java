package io.github.zhoujunlin94.example.byte_buddy;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author zhoujunlin
 * @date 2024年04月01日 16:36
 * @desc
 */
public class TestByteBuddy {


    @SneakyThrows
    public static void main(String[] args) {
        String ret = new ByteBuddy()
                // 找到Object类的toString方法 拦截
                .subclass(Object.class)
                .method(named("toString"))
                // 拦截返回值为hello world
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(TestByteBuddy.class.getClassLoader())
                .getLoaded()
                .newInstance()
                .toString();

        System.out.println(ret);

    }

}
