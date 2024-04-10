package io.github.zhoujunlin94.example.web.springmvc.handlermappingadapter;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * @author zhoujunlin
 * @date 2024年03月27日 9:41
 * @desc
 */
public class TestRouterFunctionHandler {

    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext applicationContext =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig03.class);

        /**
         * RouterFunctionMapping 通过 RequestPredicates 映射路径
         * handler需要实现HandlerFunction接口
         * HandlerFunctionAdapter调用这些handler
         *
         * RequestMappingHandlerMapping 以@RequestMapping以及它的派生注解作为映射路径
         * 控制器具体方法作为handler
         * MyRequestMappingHandlerAdapter调用这些handler
         */

    }


}
