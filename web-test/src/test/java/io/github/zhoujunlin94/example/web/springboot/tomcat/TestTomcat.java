package io.github.zhoujunlin94.example.web.springboot.tomcat;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.Set;

/**
 * @author zhoujunlin
 * @date 2024年03月29日 13:36
 * @desc
 */
public class TestTomcat {

    public static void main(String[] args) {
        //test01();
        //test02();
        test03();
    }

    @SuppressWarnings("all")
    @SneakyThrows
    private static void test03() {
        // 1. 创建Tomcat对象
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcat");

        // 2. 创建项目文件夹  即docBase
        File docBase = Files.createTempDirectory("boot.").toFile();
        // 程序退出时 删除
        docBase.deleteOnExit();

        // 3. 创建tomcat项目  tomcat中叫context
        Context context = tomcat.addContext("", docBase.getAbsolutePath());

        // 4. 编程添加Servlet   servlet即可以是传统的deploy的方法发布(基于web.xml) 也可以是动态的编程添加
        WebApplicationContext webApplicationContext = getWebApplicationContext();
        context.addServletContainerInitializer(new ServletContainerInitializer() {
            @Override
            public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
                // 自定义传统servelt
                ctx.addServlet("a", new HelloServlet()).addMapping("/hello");
                // dispatcherServlet
//                DispatcherServlet dispatcherServlet = webApplicationContext.getBean(DispatcherServlet.class);
//                ctx.addServlet("dispatcherServlet", dispatcherServlet).addMapping("/");

                // 向ServletContext注册所有ServletRegistrationBean  包含了DispatcherServlet
                for (ServletRegistrationBean servletRegistrationBean : webApplicationContext.getBeansOfType(ServletRegistrationBean.class).values()) {
                    servletRegistrationBean.onStartup(ctx);
                }

            }
        }, CollUtil.newHashSet());

        // 5. 启动tomcat
        tomcat.start();

        // 6. 创建连接器  添加协议与端口
        Connector connector = new Connector(new Http11Nio2Protocol());
        connector.setPort(8080);
        tomcat.setConnector(connector);
    }

    private static WebApplicationContext getWebApplicationContext() {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(Config.class);
        webApplicationContext.refresh();
        return webApplicationContext;
    }


    @Configuration
    static class Config {

        @Bean
        // 需要为DispatcherServlet提供 AnnotationConfigWebApplicationContext,  否则默认选择  XmlWebApplicationContext
        public DispatcherServlet dispatcherServlet(WebApplicationContext webApplicationContext) {
            return new DispatcherServlet(webApplicationContext);
        }

        @Bean
        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }

        @Bean
        public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
            RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
            requestMappingHandlerAdapter.setMessageConverters(CollUtil.newArrayList(new MappingJackson2HttpMessageConverter()));
            return requestMappingHandlerAdapter;
        }

        @RestController
        static class MyController {

            @GetMapping("/hello2")
            public Map<String, String> hello2() {
                return ImmutableMap.of("hello2", "tomcat & spring");
            }

        }

    }


    @SneakyThrows
    private static void test02() {
        // 1. 创建Tomcat对象
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcat");

        // 2. 创建项目文件夹  即docBase
        File docBase = Files.createTempDirectory("boot.").toFile();
        // 程序退出时 删除
        docBase.deleteOnExit();

        // 3. 创建tomcat项目  tomcat中叫context
        Context context = tomcat.addContext("", docBase.getAbsolutePath());

        // 4. 编程添加Servlet   servlet即可以是传统的deploy的方法发布(基于web.xml) 也可以是动态的编程添加
        context.addServletContainerInitializer(new ServletContainerInitializer() {
            @Override
            public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
                ctx.addServlet("a", new HelloServlet()).addMapping("/hello");
            }
        }, CollUtil.newHashSet());

        // 5. 启动tomcat
        tomcat.start();

        // 6. 创建连接器  添加协议与端口
        Connector connector = new Connector(new Http11Nio2Protocol());
        connector.setPort(8080);
        tomcat.setConnector(connector);
    }


    @SneakyThrows
    private static void test01() {
        // 1. 创建Tomcat对象
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcat");

        // 2. 创建项目文件夹  即docBase
        File docBase = Files.createTempDirectory("boot.").toFile();
        // 程序退出时 删除
        docBase.deleteOnExit();

        // 3. 创建tomcat项目  tomcat中叫context
        tomcat.addContext("", docBase.getAbsolutePath());

        // 4. 编程添加Servlet   servlet即可以是传统的deploy的方法发布(基于web.xml) 也可以是动态的编程添加

        // 5. 启动tomcat
        tomcat.start();

        // 6. 创建连接器  添加协议与端口
        Connector connector = new Connector(new Http11Nio2Protocol());
        connector.setPort(8080);
        tomcat.setConnector(connector);
    }


}
