package io.github.zhoujunlin94.example.web.springmvc.argumentresolver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhoujunlin
 * @date 2024年03月20日 20:00
 * @desc
 */
@Slf4j
public class TestWebDataBinder {

    @SneakyThrows
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebConfig2.class);

        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setApplicationContext(applicationContext);
        requestMappingHandlerAdapter.afterPropertiesSet();

        // 全局的
        log.warn("刚开始...");
        showBindMethods(requestMappingHandlerAdapter);

        Method getDataBinderFactoryMethod = RequestMappingHandlerAdapter.class.getDeclaredMethod("getDataBinderFactory", HandlerMethod.class);
        getDataBinderFactoryMethod.setAccessible(true);

        // 模拟调用Controller1局部方法foo
        getDataBinderFactoryMethod.invoke(requestMappingHandlerAdapter, new HandlerMethod(new WebConfig2.Controller1(), WebConfig2.Controller1.class.getMethod("foo")));
        showBindMethods(requestMappingHandlerAdapter);

        // 模拟调用Controller2局部方法bar
        getDataBinderFactoryMethod.invoke(requestMappingHandlerAdapter, new HandlerMethod(new WebConfig2.Controller2(), WebConfig2.Controller2.class.getMethod("bar")));
        showBindMethods(requestMappingHandlerAdapter);

        applicationContext.close();
    }

    @SneakyThrows
    public static void showBindMethods(RequestMappingHandlerAdapter handlerAdapter) {
        Field initBinderAdviceCacheField = RequestMappingHandlerAdapter.class.getDeclaredField("initBinderAdviceCache");
        initBinderAdviceCacheField.setAccessible(true);

        Map<ControllerAdviceBean, Set<Method>> globalMap = (Map<ControllerAdviceBean, Set<Method>>) initBinderAdviceCacheField.get(handlerAdapter);
        log.warn("ControllerAdvice全局的@InitBinder方法:{}", globalMap.values().stream().flatMap(methods ->
                methods.stream().map(Method::getName)).collect(Collectors.toList()));

        Field initBinderCacheField = RequestMappingHandlerAdapter.class.getDeclaredField("initBinderCache");
        initBinderCacheField.setAccessible(true);

        Map<Class<?>, Set<Method>> localMap = (Map<Class<?>, Set<Method>>) initBinderCacheField.get(handlerAdapter);
        log.warn("控制器局部的@InitBinder方法:{}", localMap.values().stream().flatMap(methods ->
                methods.stream().map(Method::getName)).collect(Collectors.toList()));
    }

}
