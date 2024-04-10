package io.github.zhoujunlin94.example.web.springmvc.handlermappingadapter;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * @author zhoujunlin
 * @date 2024年03月27日 9:41
 * @desc
 */
public class TestResourceHandler {

    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext applicationContext =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig04.class);

        /**
         * SimpleUrlHandlerMapping 以ResourceHttpRequestHandler的bean名字作为匹配映射路径 在指定的文件夹下查找文件
         * 这些bean本身作为handler
         * HttpRequestHandlerAdapter调用这些handler
         *
         * RequestMappingHandlerMapping 以@RequestMapping以及它的派生注解作为映射路径
         * 控制器具体方法作为handler
         * MyRequestMappingHandlerAdapter调用这些handler
         */

    }


}
