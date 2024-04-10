package io.github.zhoujunlin94.example.web.springmvc.dispatchservlet;

import java.lang.annotation.*;

/**
 * 自定义方法返回值注解  用户测试自定义方法返回值处理器
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Yml {
}
