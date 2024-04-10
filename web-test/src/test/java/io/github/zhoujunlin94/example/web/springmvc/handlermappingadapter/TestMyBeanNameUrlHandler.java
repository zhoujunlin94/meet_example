package io.github.zhoujunlin94.example.web.springmvc.handlermappingadapter;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * @author zhoujunlin
 * @date 2024年03月27日 9:41
 * @desc
 */
public class TestMyBeanNameUrlHandler {

    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext applicationContext =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig02.class);
    }


}
