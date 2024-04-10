package io.github.zhoujunlin94.example.web.spring.aop.cglib;

/**
 * @author zhoujunlin
 * @date 2024/3/3 20:41
 * @desc
 */
public class Target {

    public void save() {
        System.out.println("Target.save()");
    }

    public void save(int i) {
        System.out.println("Target.save i(" + i + ")");
    }

    public void save(long i) {
        System.out.println("Target.save l(" + i + ")");

    }
}
