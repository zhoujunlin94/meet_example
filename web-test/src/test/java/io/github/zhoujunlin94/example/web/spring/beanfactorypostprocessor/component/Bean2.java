package io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor.component;

import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024/2/25 20:18
 * @desc
 */
@Component
public class Bean2 {

    public void Bean2() {
        System.out.println("Bean2被Spring管理了");
    }

}
