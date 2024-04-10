package io.github.zhoujunlin94.example.web.springmvc.dispatchservlet;

import lombok.SneakyThrows;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2024年03月12日 14:32
 * @desc
 */
public class TestDispatchServlet {

    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext applicationContext =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

//        testHandlerMethod(applicationContext);
//        test1(applicationContext);
//        test2(applicationContext);
//        testAllArgumentResolver(applicationContext);
//        test3(applicationContext);
        test4(applicationContext);
    }

    public static void testHandlerMethod(ApplicationContext applicationContext) {
        // 作用: 解析@RequestMapping以及其派生注解  生成请求路径与处理方法的映射关系  在初始化时就生成了
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        handlerMethods.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });

        /**
         * {GET [/test1]}=io.github.zhoujunlin94.example.web.springmvc.dispatchservlet.TestController#test1()
         * {POST [/test2]}=io.github.zhoujunlin94.example.web.springmvc.dispatchservlet.TestController#test2(String)
         * { [/test4]}=io.github.zhoujunlin94.example.web.springmvc.dispatchservlet.TestController#test4()
         * {PUT [/test3]}=io.github.zhoujunlin94.example.web.springmvc.dispatchservlet.TestController#test3(String)
         */

    }

    @SneakyThrows
    public static void test1(ApplicationContext applicationContext) {
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test1");
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 获取Mock请求对应的处理器方法  放回执行器链对象
        HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
        System.out.println("handlerExecutionChain = " + handlerExecutionChain);
        /**
         * 对应一个处理器方法和0个拦截器
         * HandlerExecutionChain with [io.github.zhoujunlin94.example.web.springmvc.dispatchservlet.TestController#test1()] and 0 interceptors
         */
        System.out.println("========>");
        MyRequestMappingHandlerAdapter handlerAdapter = applicationContext.getBean(MyRequestMappingHandlerAdapter.class);
        handlerAdapter.invokeHandlerMethod(request, response, ((HandlerMethod) handlerExecutionChain.getHandler()));
    }

    @SneakyThrows
    public static void test2(ApplicationContext applicationContext) {
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/test2");
        request.addParameter("name", "zhou");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
        System.out.println("========>");
        MyRequestMappingHandlerAdapter handlerAdapter = applicationContext.getBean(MyRequestMappingHandlerAdapter.class);
        handlerAdapter.invokeHandlerMethod(request, response, ((HandlerMethod) handlerExecutionChain.getHandler()));
    }

    public static void testAllArgumentResolver(ApplicationContext applicationContext) {
        // 获取所有参数解析器
        MyRequestMappingHandlerAdapter handlerAdapter = applicationContext.getBean(MyRequestMappingHandlerAdapter.class);
        handlerAdapter.getArgumentResolvers().forEach(System.out::println);
    }

    @SneakyThrows
    public static void test3(ApplicationContext applicationContext) {
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        MockHttpServletRequest request = new MockHttpServletRequest("PUT", "/test3");
        request.addParameter("token", "混淆视听");
        request.addHeader("token", "某个token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
        System.out.println("========>");
        MyRequestMappingHandlerAdapter handlerAdapter = applicationContext.getBean(MyRequestMappingHandlerAdapter.class);
        handlerAdapter.invokeHandlerMethod(request, response, ((HandlerMethod) handlerExecutionChain.getHandler()));
    }

    @SneakyThrows
    public static void test4(ApplicationContext applicationContext) {
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        MockHttpServletRequest request = new MockHttpServletRequest("DELETE", "/test4");
        request.addParameter("token", "混淆视听");
        request.addHeader("token", "某个token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
        System.out.println("========>");
        MyRequestMappingHandlerAdapter handlerAdapter = applicationContext.getBean(MyRequestMappingHandlerAdapter.class);
        handlerAdapter.invokeHandlerMethod(request, response, ((HandlerMethod) handlerExecutionChain.getHandler()));

        byte[] ret = response.getContentAsByteArray();
        System.out.println(new String(ret, StandardCharsets.UTF_8));

    }

}
