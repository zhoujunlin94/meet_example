package io.github.zhoujunlin94.example.web.springmvc.dispatchservlet;

import java.lang.annotation.*;

/**
 * 自定义参数注解：用于将请求头中的token参数解析出来并放入方法入参（参数解析器）
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {
}
