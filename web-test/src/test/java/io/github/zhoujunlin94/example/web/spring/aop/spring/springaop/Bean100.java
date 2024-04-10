package io.github.zhoujunlin94.example.web.spring.aop.spring.springaop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhoujunlin
 * @date 2024/3/31 13:29
 * @desc
 */
@Slf4j
@Component
public class Bean100 {

    protected Bean200 bean2;
    protected boolean initialized;

    @Autowired
    public void setBean2(Bean200 bean2) {
        log.warn("Bean1#setBean2({})", bean2);
        this.bean2 = bean2;
    }

    public Bean200 getBean2() {
        log.warn("Bean2#getBean2()");
        return bean2;
    }

    @PostConstruct
    public void init() {
        log.warn("Bean1#init()");
        this.initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }


    // 成员方法会被增强
    public void m1() {
        System.out.println("Bean100.m1");
    }

    // final  static private方法均不会增强
    public final void m2() {
        System.out.println("Bean100.m2");
    }

    public static void m3() {
        System.out.println("Bean100.m3");
    }

    private void m4() {
        System.out.println("Bean100.m4");
    }

}
