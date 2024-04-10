package io.github.zhoujunlin94.example.web.spring.applicationcontext;

import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author zhoujunlin
 * @date 2024/2/22 22:29
 * @desc
 */
public class ApplicationContextTest02 {

    @Test
    public void testLoadBeanDefinitions() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        System.out.println("加载xml之前。。。");
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("bean01.xml"));

        System.out.println("加载xml之后。。。");
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println(beanFactory.getBean(Bean02.class).getBean01());
    }

    @Test
    public void testClassPathXmlApplicationContext() {
        // ClassPathXmlApplicationContext加载BeanDefinition到Bean容器
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean01.xml");
        for (String beanDefinitionName : classPathXmlApplicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println(classPathXmlApplicationContext.getBean(Bean02.class).getBean01());
    }

    @Test
    public void testFileSystemXmlApplicationContext() {
        FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext("/src/test/resources/bean01.xml");
        for (String beanDefinitionName : fileSystemXmlApplicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println(fileSystemXmlApplicationContext.getBean(Bean02.class).getBean01());
    }

    @Test
    public void testAnnotationConfigApplicationContext() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
        /**
         * 多加载了内置处理器
         * internalConfigurationAnnotationProcessor、internalAutowiredAnnotationProcessor、internalCommonAnnotationProcessor
         * internalEventListenerProcessor、internalEventListenerFactory
         * 相当于多了此功能
         * AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
         */
        for (String beanDefinitionName : annotationConfigApplicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println(annotationConfigApplicationContext.getBean(Bean02.class).getBean01());
    }


    public static void main(String[] args) {
        // 启动web服务
        AnnotationConfigServletWebServerApplicationContext webServerApplicationContext = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }


    static class Bean01 {
    }

    @Data
    static class Bean02 {
        private Bean01 bean01;
    }

    @Configuration
    static class Config {
        @Bean
        public Bean01 bean01() {
            return new Bean01();
        }

        @Bean
        public Bean02 bean02(Bean01 bean01) {
            Bean02 bean02 = new Bean02();
            bean02.setBean01(bean01);
            return bean02;
        }
    }

    @Configuration
    static class WebConfig {

        @Bean
        public ServletWebServerFactory tomcatServletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public DispatcherServlet dispatcherServlet() {
            return new DispatcherServlet();
        }

        @Bean
        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }

        @Bean("/hello")
        public Controller hello() {
            return (request, response) -> {
                response.getWriter().print("hello world");
                return null;
            };
        }

    }

}



