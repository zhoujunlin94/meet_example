package io.github.zhoujunlin94.example.web.springmvc.returnvaluehandler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @author zhoujunlin
 * @date 2024年03月22日 10:08
 * @desc
 */
@Slf4j
public class TestReturnValueHandler {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
        test7();
    }

    @SneakyThrows
    private static void test1() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(WebConfig.class);
        // 只探讨返回值处理器  所有测试方法都不带参数
        Method method = Controller.class.getMethod("test1");
        Controller controller = new Controller();
        // 得到返回值
        Object returnValue = method.invoke(controller);
        // 拿到返回值类型
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        MethodParameter returnType = handlerMethod.getReturnType();
        // 模型视图容器
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 返回值处理器
        HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = returnValueHandlerComposite();
        if (returnValueHandlerComposite.supportsReturnType(returnType)) {
            returnValueHandlerComposite.handleReturnValue(
                    returnValue, returnType, modelAndViewContainer, servletWebRequest
            );
            System.out.println(modelAndViewContainer.getModel());
            System.out.println(modelAndViewContainer.getViewName());
            renderValue(modelAndViewContainer, servletWebRequest, annotationConfigApplicationContext);
        }

    }


    @SneakyThrows
    private static void test2() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(WebConfig.class);
        // 只探讨返回值处理器  所有测试方法都不带参数
        Method method = Controller.class.getMethod("test2");
        Controller controller = new Controller();
        // 得到返回值
        Object returnValue = method.invoke(controller);
        // 拿到返回值类型
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        MethodParameter returnType = handlerMethod.getReturnType();
        // 模型视图容器
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 返回值处理器
        HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = returnValueHandlerComposite();
        if (returnValueHandlerComposite.supportsReturnType(returnType)) {
            returnValueHandlerComposite.handleReturnValue(
                    returnValue, returnType, modelAndViewContainer, servletWebRequest
            );
            System.out.println(modelAndViewContainer.getModel());
            System.out.println(modelAndViewContainer.getViewName());
            renderValue(modelAndViewContainer, servletWebRequest, annotationConfigApplicationContext);
        }

    }

    @SneakyThrows
    private static void test3() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(WebConfig.class);
        // 只探讨返回值处理器  所有测试方法都不带参数
        Method method = Controller.class.getMethod("test3");
        Controller controller = new Controller();
        // 得到返回值
        Object returnValue = method.invoke(controller);
        // 拿到返回值类型
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        MethodParameter returnType = handlerMethod.getReturnType();
        // 模型视图容器
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        // 模拟将路径放入RequestAttribute中  模拟视图名字
        request.setRequestURI("test3");
        UrlPathHelper.defaultInstance.resolveAndCacheLookupPath(request);
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setContentType("text/html;charset=utf-8");
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 返回值处理器
        HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = returnValueHandlerComposite();
        if (returnValueHandlerComposite.supportsReturnType(returnType)) {
            returnValueHandlerComposite.handleReturnValue(
                    returnValue, returnType, modelAndViewContainer, servletWebRequest
            );
            System.out.println(modelAndViewContainer.getModel());
            System.out.println(modelAndViewContainer.getViewName());
            renderValue(modelAndViewContainer, servletWebRequest, annotationConfigApplicationContext);
        }

    }

    @SneakyThrows
    private static void test4() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(WebConfig.class);
        // 只探讨返回值处理器  所有测试方法都不带参数
        Method method = Controller.class.getMethod("test4");
        Controller controller = new Controller();
        // 得到返回值
        Object returnValue = method.invoke(controller);
        // 拿到返回值类型
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        MethodParameter returnType = handlerMethod.getReturnType();
        // 模型视图容器
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        // 模拟将路径放入RequestAttribute中  模拟视图名字
        request.setRequestURI("test4");
        UrlPathHelper.defaultInstance.resolveAndCacheLookupPath(request);
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setContentType("text/html;charset=utf-8");
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 返回值处理器
        HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = returnValueHandlerComposite();
        if (returnValueHandlerComposite.supportsReturnType(returnType)) {
            returnValueHandlerComposite.handleReturnValue(
                    returnValue, returnType, modelAndViewContainer, servletWebRequest
            );
            System.out.println(modelAndViewContainer.getModel());
            System.out.println(modelAndViewContainer.getViewName());
            renderValue(modelAndViewContainer, servletWebRequest, annotationConfigApplicationContext);
        }

    }

    @SneakyThrows
    private static void test5() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(WebConfig.class);
        // 只探讨返回值处理器  所有测试方法都不带参数
        Method method = Controller.class.getMethod("test5");
        Controller controller = new Controller();
        // 得到返回值
        Object returnValue = method.invoke(controller);
        // 拿到返回值类型
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        MethodParameter returnType = handlerMethod.getReturnType();
        // 模型视图容器
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 返回值处理器
        HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = returnValueHandlerComposite();
        if (returnValueHandlerComposite.supportsReturnType(returnType)) {
            returnValueHandlerComposite.handleReturnValue(
                    returnValue, returnType, modelAndViewContainer, servletWebRequest
            );
            System.out.println(modelAndViewContainer.getModel());
            System.out.println(modelAndViewContainer.getViewName());
            renderValue(modelAndViewContainer, servletWebRequest, annotationConfigApplicationContext);
        }

    }


    @SneakyThrows
    private static void test6() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(WebConfig.class);
        // 只探讨返回值处理器  所有测试方法都不带参数
        Method method = Controller.class.getMethod("test6");
        Controller controller = new Controller();
        // 得到返回值
        Object returnValue = method.invoke(controller);
        // 拿到返回值类型
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        MethodParameter returnType = handlerMethod.getReturnType();
        // 模型视图容器
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 返回值处理器
        HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = returnValueHandlerComposite();
        if (returnValueHandlerComposite.supportsReturnType(returnType)) {
            returnValueHandlerComposite.handleReturnValue(
                    returnValue, returnType, modelAndViewContainer, servletWebRequest
            );
            System.out.println(modelAndViewContainer.getModel());
            System.out.println(modelAndViewContainer.getViewName());
            renderValue(modelAndViewContainer, servletWebRequest, annotationConfigApplicationContext);
        }

    }

    @SneakyThrows
    private static void test7() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(WebConfig.class);
        // 只探讨返回值处理器  所有测试方法都不带参数
        Method method = Controller.class.getMethod("test7");
        Controller controller = new Controller();
        // 得到返回值
        Object returnValue = method.invoke(controller);
        // 拿到返回值类型
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        MethodParameter returnType = handlerMethod.getReturnType();
        // 模型视图容器
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();
        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        // 返回值处理器
        HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = returnValueHandlerComposite();
        if (returnValueHandlerComposite.supportsReturnType(returnType)) {
            returnValueHandlerComposite.handleReturnValue(
                    returnValue, returnType, modelAndViewContainer, servletWebRequest
            );
            System.out.println(modelAndViewContainer.getModel());
            System.out.println(modelAndViewContainer.getViewName());
            renderValue(modelAndViewContainer, servletWebRequest, annotationConfigApplicationContext);
        }

    }

    @SneakyThrows
    private static void renderValue(ModelAndViewContainer modelAndViewContainer, ServletWebRequest servletWebRequest,
                                    AnnotationConfigApplicationContext applicationContext) {

        try {
            log.warn("=========>渲染视图内容");
            HttpServletRequest request = servletWebRequest.getRequest();
            MockHttpServletResponse response = (MockHttpServletResponse) servletWebRequest.getResponse();
            String viewName = modelAndViewContainer.getViewName();
            if (StrUtil.isBlank(viewName)) {
                viewName = ((String) request.getAttribute(UrlPathHelper.PATH_ATTRIBUTE));
                modelAndViewContainer.setViewName(viewName);
                log.warn("没有获取到视图名,获取默认视图:{}", viewName);
            }
            if (!modelAndViewContainer.isRequestHandled()) {
                // 获取 View
                FreeMarkerViewResolver freeMarkerViewResolver = applicationContext.getBean(FreeMarkerViewResolver.class);
                View view = freeMarkerViewResolver.resolveViewName(viewName, RequestContextUtils.getLocale(request));
                view.render(modelAndViewContainer.getModel(), request, response);
            }
            for (String headerName : response.getHeaderNames()) {
                System.out.println(headerName + "=" + response.getHeaders(headerName));
            }
            System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

        } catch (Exception e) {
            // 处理渲染异常
            e.printStackTrace(); // 这里需要根据实际情况处理异常
        }
    }

    private static HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = new HandlerMethodReturnValueHandlerComposite();
        returnValueHandlerComposite.addHandlers(CollUtil.newArrayList(
                // 返回值：ModelAndView    test1
                new ModelAndViewMethodReturnValueHandler(),
                // 返回值字符串：视图名    test2
                new ViewNameMethodReturnValueHandler(),
                // 返回模型数据  带@ModelAttribute注解   test3
                new ServletModelAttributeMethodProcessor(false),
                // 返回值：HttpEntity    test5
                new HttpEntityMethodProcessor(CollUtil.newArrayList(jackson2HttpMessageConverter)),
                // 返回值：HttpHeaders   test6
                new HttpHeadersReturnValueHandler(),
                // 返回值json: 带@ResponseBody注解   test7
                new RequestResponseBodyMethodProcessor(CollUtil.newArrayList(jackson2HttpMessageConverter)),
                // 返回模型数据  不带@ModelAttribute注解   test4
                new ServletModelAttributeMethodProcessor(true)
        ));
        return returnValueHandlerComposite;
    }

    @Slf4j
    static class Controller {

        public ModelAndView test1() {
            log.warn("test1()");
            // 添加视图名
            ModelAndView modelAndView = new ModelAndView("view1");
            // 添加模型名
            modelAndView.addObject("name", "张三");
            return modelAndView;
        }

        public String test2() {
            log.warn("test2()");
            // 返回视图名   只有视图 没有模型数据
            return "view2";
        }

        @ModelAttribute
        // 没有视图 默认将请求路径当作视图名
        //@RequestMapping("/test3")
        public User test3() {
            log.warn("test3()");
            // 只有模型数据  没有视图
            return new User("李四", 20);
        }

        public User test4() {
            log.warn("test4()");
            // 只有模型数据  没有视图
            return new User("王五", 30);
        }

        public HttpEntity<User> test5() {
            log.warn("test5()");
            return new HttpEntity<>(new User("赵六", 40));
        }

        public HttpHeaders test6() {
            log.warn("test6()");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "text/html");
            return httpHeaders;
        }

        @ResponseBody
        public User test7() {
            log.warn("test7()");
            return new User("钱七", 50);
        }

    }

}

