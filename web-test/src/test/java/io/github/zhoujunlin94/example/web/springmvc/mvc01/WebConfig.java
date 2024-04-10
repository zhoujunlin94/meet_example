package io.github.zhoujunlin94.example.web.springmvc.mvc01;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhoujunlin
 * @date 2024/3/21 22:36
 * @desc
 */
@Configuration
public class WebConfig {

    @ControllerAdvice
    static class MyControllerAdvice {

        @ModelAttribute("b")
        public String bar() {
            return "bar";
        }

    }

    @Controller
    static class Controller1 {

        @ResponseStatus(HttpStatus.OK)  // 添加此注解  这里不测试返回值处理器
        public ModelAndView foo(@ModelAttribute("u") User user) {
            System.out.println("Controller1.foo");
            System.out.println("user = " + user);
            return null;
        }

    }

    @Data
    static class User {
        private String name;
    }
}
