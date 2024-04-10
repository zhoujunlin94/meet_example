package io.github.zhoujunlin94.example.web.springmvc.exphandler;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2024/3/24 21:49
 * @desc
 */
public class TestExceptionHandler {


    public static void main(String[] args) {
//      test1();
//        test2();
//        test3();
//        test4();
        test5();
    }

    @SneakyThrows
    public static void test1() {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(CollUtil.newArrayList(new MappingJackson2HttpMessageConverter()));
        resolver.afterPropertiesSet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerMethod handlerMethod = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
        ArithmeticException arithmeticException = new ArithmeticException("模拟当前方法抛出一个异常");

        resolver.resolveException(request, response, handlerMethod, arithmeticException);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

    }

    @SneakyThrows
    public static void test2() {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(CollUtil.newArrayList(new MappingJackson2HttpMessageConverter()));
        resolver.afterPropertiesSet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerMethod handlerMethod = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
        ArithmeticException arithmeticException = new ArithmeticException("模拟当前方法抛出一个异常");

        ModelAndView modelAndView = resolver.resolveException(request, response, handlerMethod, arithmeticException);

        System.out.println(modelAndView.getViewName());
        System.out.println(modelAndView.getModel());

    }

    @SneakyThrows
    public static void test3() {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(CollUtil.newArrayList(new MappingJackson2HttpMessageConverter()));
        resolver.afterPropertiesSet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerMethod handlerMethod = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
        Exception exception = new Exception("e1", new RuntimeException("e2", new IOException("e3")));

        resolver.resolveException(request, response, handlerMethod, exception);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

    }


    @SneakyThrows
    public static void test4() {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(CollUtil.newArrayList(new MappingJackson2HttpMessageConverter()));
        resolver.afterPropertiesSet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerMethod handlerMethod = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
        Exception exception = new Exception("e4");

        resolver.resolveException(request, response, handlerMethod, exception);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

    }

    @SneakyThrows
    public static void test5() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebConfig.class);
        ExceptionHandlerExceptionResolver resolver = applicationContext.getBean(ExceptionHandlerExceptionResolver.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerMethod handlerMethod = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
        ArithmeticException arithmeticException = new ArithmeticException("模拟当前方法抛出一个异常");

        resolver.resolveException(request, response, handlerMethod, arithmeticException);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

    }


    static class Controller1 {
        public void foo() {

        }

        // @ExceptionHandler
        @ResponseBody
        public Map<String, Object> handle1(ArithmeticException arithmeticException) {
            // 局部异常处理器-json
            return ImmutableMap.of("error", arithmeticException.getMessage());
        }

        //@ExceptionHandler
        public ModelAndView handle2(ArithmeticException arithmeticException) {
            // 局部异常处理器-ModelAndView
            return new ModelAndView("handle2", ImmutableMap.of("error", arithmeticException.getMessage()));
        }

        //@ExceptionHandler
        @ResponseBody
        public Map<String, Object> handle3(IOException ioException) {
            // 局部异常处理器-json
            return ImmutableMap.of("error", ioException.getMessage());
        }

        //@ExceptionHandler
        @ResponseBody
        public Map<String, Object> handle4(Exception e, HttpServletRequest request) {
            System.out.println("=======>" + request);
            // 局部异常处理器-json
            return ImmutableMap.of("error", e.getMessage());
        }


    }


}
