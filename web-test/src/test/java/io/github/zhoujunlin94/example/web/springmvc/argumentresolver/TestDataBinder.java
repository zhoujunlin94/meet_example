package io.github.zhoujunlin94.example.web.springmvc.argumentresolver;

import cn.hutool.core.collection.CollUtil;
import lombok.*;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.Date;

/**
 * @author zhoujunlin
 * @date 2024/3/19 22:02
 * @desc 测试数据绑定
 */
public class TestDataBinder {

    public static void main(String[] args) {
        //testDataBinder();
        //testDataBinder2();
        //testWebDataBinder();
        //testWebDataBinder2();
        //testWebDataBinderFactory();
        //testInitBinder();
        //testConversionService();
        //testInitBinderAndConversionService();
        //testDefaultConversionService();
        testApplicationConversionService();
    }

    private static void testDataBinder() {
        Bean1 target = new Bean1();
        // 默认走反射调用set方法的方式  即BeanWrapperImpl
        DataBinder dataBinder = new DataBinder(target);

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("a", "123");
        propertyValues.add("b", "456");
        propertyValues.add("c", "2023/03/19");

        dataBinder.bind(propertyValues);
        System.out.println(target);
    }

    private static void testDataBinder2() {
        Bean2 target = new Bean2();
        DataBinder dataBinder = new DataBinder(target);
        // 改成走DirectFieldAccessor方式
        dataBinder.initDirectFieldAccess();

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("a", "123");
        propertyValues.add("b", "456");
        propertyValues.add("c", "2023/03/19");

        dataBinder.bind(propertyValues);
        System.out.println(target);
    }

    private static void testWebDataBinder() {
        Bean1 target = new Bean1();
        ServletRequestDataBinder servletRequestDataBinder = new ServletRequestDataBinder(target);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("a", "123");
        request.setParameter("b", "456");
        request.setParameter("c", "2023/03/19");

        ServletRequestParameterPropertyValues propertyValues = new ServletRequestParameterPropertyValues(request);
        servletRequestDataBinder.bind(propertyValues);

        System.out.println("target = " + target);
    }

    private static void testWebDataBinder2() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        // 无法解析
        request.setParameter("birthday", "1994|07|08");
        request.setParameter("address.name", "上海");


        User target = new User();
        ServletRequestDataBinder servletRequestDataBinder = new ServletRequestDataBinder(target);
        ServletRequestParameterPropertyValues propertyValues = new ServletRequestParameterPropertyValues(request);
        servletRequestDataBinder.bind(propertyValues);

        System.out.println("target = " + target);
    }

    @SneakyThrows
    private static void testWebDataBinderFactory() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        // 无法解析
        request.setParameter("birthday", "1994|07|08");
        request.setParameter("address.name", "上海");

        User target = new User();
        ServletRequestDataBinderFactory servletRequestDataBinderFactory = new ServletRequestDataBinderFactory(null, null);
        WebDataBinder webDataBinder = servletRequestDataBinderFactory.createBinder(new ServletWebRequest(request), target, "target");

        ServletRequestParameterPropertyValues propertyValues = new ServletRequestParameterPropertyValues(request);
        webDataBinder.bind(propertyValues);
        System.out.println("target = " + target);
    }

    @SneakyThrows
    private static void testInitBinder() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "1994|07|08");
        request.setParameter("address.name", "上海");

        User target = new User();

        InvocableHandlerMethod invocableHandlerMethod = new InvocableHandlerMethod(new MyController(), MyController.class.getMethod("a", WebDataBinder.class));
        ServletRequestDataBinderFactory servletRequestDataBinderFactory = new ServletRequestDataBinderFactory(
                CollUtil.newArrayList(invocableHandlerMethod),
                null);
        WebDataBinder webDataBinder = servletRequestDataBinderFactory.createBinder(new ServletWebRequest(request), target, "target");

        ServletRequestParameterPropertyValues propertyValues = new ServletRequestParameterPropertyValues(request);
        webDataBinder.bind(propertyValues);
        System.out.println("target = " + target);
    }

    @SneakyThrows
    private static void testConversionService() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "1994|07|08");
        request.setParameter("address.name", "上海");

        User target = new User();

        FormattingConversionService formattingConversionService = new FormattingConversionService();
        formattingConversionService.addFormatter(new MyDateFormatter("FormattingConversionService进行扩展"));

        ConfigurableWebBindingInitializer configurableWebBindingInitializer = new ConfigurableWebBindingInitializer();
        configurableWebBindingInitializer.setConversionService(formattingConversionService);

        ServletRequestDataBinderFactory servletRequestDataBinderFactory = new ServletRequestDataBinderFactory(
                null,
                configurableWebBindingInitializer);
        WebDataBinder webDataBinder = servletRequestDataBinderFactory.createBinder(new ServletWebRequest(request), target, "target");

        ServletRequestParameterPropertyValues propertyValues = new ServletRequestParameterPropertyValues(request);
        webDataBinder.bind(propertyValues);
        System.out.println("target = " + target);
    }

    @SneakyThrows
    private static void testInitBinderAndConversionService() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "1994|07|08");
        request.setParameter("address.name", "上海");

        User target = new User();

        InvocableHandlerMethod invocableHandlerMethod = new InvocableHandlerMethod(new MyController(), MyController.class.getMethod("a", WebDataBinder.class));

        FormattingConversionService formattingConversionService = new FormattingConversionService();
        formattingConversionService.addFormatter(new MyDateFormatter("FormattingConversionService进行扩展"));
        ConfigurableWebBindingInitializer configurableWebBindingInitializer = new ConfigurableWebBindingInitializer();
        configurableWebBindingInitializer.setConversionService(formattingConversionService);

        // @InitBinder优先级比ConversionService高
        ServletRequestDataBinderFactory servletRequestDataBinderFactory = new ServletRequestDataBinderFactory(
                CollUtil.newArrayList(invocableHandlerMethod),
                configurableWebBindingInitializer);
        WebDataBinder webDataBinder = servletRequestDataBinderFactory.createBinder(new ServletWebRequest(request), target, "target");

        ServletRequestParameterPropertyValues propertyValues = new ServletRequestParameterPropertyValues(request);
        webDataBinder.bind(propertyValues);
        System.out.println("target = " + target);
    }

    @SneakyThrows
    private static void testDefaultConversionService() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "1994|07|08");
        request.setParameter("address.name", "上海");

        User target = new User();

        // 需要搭配注解  @DateTimeFormat(pattern = "yyyy|MM|dd")     springmvc
        DefaultFormattingConversionService defaultFormattingConversionService = new DefaultFormattingConversionService();
        ConfigurableWebBindingInitializer configurableWebBindingInitializer = new ConfigurableWebBindingInitializer();
        configurableWebBindingInitializer.setConversionService(defaultFormattingConversionService);

        ServletRequestDataBinderFactory servletRequestDataBinderFactory = new ServletRequestDataBinderFactory(
                null,
                configurableWebBindingInitializer);
        WebDataBinder webDataBinder = servletRequestDataBinderFactory.createBinder(new ServletWebRequest(request), target, "target");

        ServletRequestParameterPropertyValues propertyValues = new ServletRequestParameterPropertyValues(request);
        webDataBinder.bind(propertyValues);
        System.out.println("target = " + target);
    }

    @SneakyThrows
    private static void testApplicationConversionService() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "1994|07|08");
        request.setParameter("address.name", "上海");

        User target = new User();

        // 需要搭配注解  @DateTimeFormat(pattern = "yyyy|MM|dd")   springboot
        ApplicationConversionService applicationConversionService = new ApplicationConversionService();
        ConfigurableWebBindingInitializer configurableWebBindingInitializer = new ConfigurableWebBindingInitializer();
        configurableWebBindingInitializer.setConversionService(applicationConversionService);

        ServletRequestDataBinderFactory servletRequestDataBinderFactory = new ServletRequestDataBinderFactory(
                null,
                configurableWebBindingInitializer);
        WebDataBinder webDataBinder = servletRequestDataBinderFactory.createBinder(new ServletWebRequest(request), target, "target");

        ServletRequestParameterPropertyValues propertyValues = new ServletRequestParameterPropertyValues(request);
        webDataBinder.bind(propertyValues);
        System.out.println("target = " + target);
    }


    @ToString
    @Getter
    @Setter
    static class Bean1 {
        private int a;
        private String b;
        private Date c;
    }

    @ToString
    static class Bean2 {
        private int a;
        private String b;
        private Date c;
    }

    @Data
    static class User {
        @DateTimeFormat(pattern = "yyyy|MM|dd")
        private Date birthday;
        private Address address;
    }

    @Data
    static class Address {
        private String name;
    }

    static class MyController {

        @InitBinder
        public void a(WebDataBinder webDataBinder) {
            // 扩展dataBinder的转换器
            webDataBinder.addCustomFormatter(new MyDateFormatter("MyController下@InitBinder进行扩展"));
        }

    }

}



