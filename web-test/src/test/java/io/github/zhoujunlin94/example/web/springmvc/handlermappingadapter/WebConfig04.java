package io.github.zhoujunlin94.example.web.springmvc.handlermappingadapter;

import cn.hutool.core.collection.CollUtil;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.resource.CachingResourceResolver;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2024/3/21 22:36
 * @desc
 */
@Configuration
public class WebConfig04 {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory(8080);
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
        return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(ApplicationContext applicationContext) {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        Map<String, ResourceHttpRequestHandler> resourceHttpRequestHandlerMap = applicationContext.getBeansOfType(ResourceHttpRequestHandler.class);
        simpleUrlHandlerMapping.setUrlMap(resourceHttpRequestHandlerMap);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public HttpRequestHandlerAdapter httpRequestHandlerAdapter() {
        return new HttpRequestHandlerAdapter();
    }


    /**
     * 映射  /test1.html   /test2.html
     */
    @Bean("/**")
    public ResourceHttpRequestHandler htmlHandler() {
        ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
        resourceHttpRequestHandler.setLocations(CollUtil.newArrayList(
                new ClassPathResource("static/")
        ));
        resourceHttpRequestHandler.setResourceResolvers(CollUtil.newArrayList(
                // 缓存查找
                new CachingResourceResolver(new ConcurrentMapCache("resourceCache")),
                // 压缩文件查找
                new EncodedResourceResolver(),
                // 从磁盘查找
                new PathResourceResolver()
        ));
        return resourceHttpRequestHandler;
    }

    /**
     * 映射  /image/1.png   /image/2.png
     */
    @Bean("/image/**")
    public ResourceHttpRequestHandler imageHandler() {
        ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
        resourceHttpRequestHandler.setLocations(CollUtil.newArrayList(
                new ClassPathResource("image/")
        ));
        return resourceHttpRequestHandler;
    }


}
