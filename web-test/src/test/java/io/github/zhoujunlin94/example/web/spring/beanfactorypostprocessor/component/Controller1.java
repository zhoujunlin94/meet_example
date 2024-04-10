package io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor.component;

import org.springframework.stereotype.Controller;

/**
 * @author zhoujunlin
 * @date 2024/2/25 20:46
 * @desc
 */
@Controller
public class Controller1 {

    public void Controller1() {
        System.out.println("Controller1实例化被Spring管理了");
    }

}
