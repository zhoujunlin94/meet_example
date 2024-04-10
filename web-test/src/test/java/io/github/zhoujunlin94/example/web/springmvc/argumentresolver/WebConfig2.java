package io.github.zhoujunlin94.example.web.springmvc.argumentresolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author zhoujunlin
 * @date 2024/3/19 23:01
 * @desc
 */
@Configuration
public class WebConfig2 {

    /**
     * -> @InitBinder 的来源有两个
     * 1. @ControllerAdvice中的 @InitBinder方法  由RequestMappingHandlerAdapter在初始化时解析并记录
     * 2. Controller中的@InitBinder  由RequestMappingHandlerAdapter在控制器方法首次执行时解析并记录
     */

    @Controller
    static class Controller1 {
        /**
         * 添加当前Controller局部转换器
         */
        @InitBinder
        public void binder1(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("binder1转换器"));
        }

        public void foo() {

        }

    }

    @Controller
    static class Controller2 {
        /**
         * 添加当前Controller局部转换器
         */
        @InitBinder
        public void binder21(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("binder2-1转换器"));
        }

        @InitBinder
        public void binder22(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("binder2-2转换器"));
        }

        public void bar() {

        }

    }

    @ControllerAdvice
    static class MyControllerAdvice {

        /**
         * 添加全局转换器
         */
        @InitBinder
        public void binder3(WebDataBinder webDataBinder) {
            webDataBinder.addCustomFormatter(new MyDateFormatter("binder3转换器"));
        }
//    @ExceptionHandler
//    @ModelAttribute
    }

}
