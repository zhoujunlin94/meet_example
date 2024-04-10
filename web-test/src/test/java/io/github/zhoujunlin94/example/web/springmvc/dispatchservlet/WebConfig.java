package io.github.zhoujunlin94.example.web.springmvc.dispatchservlet;

import cn.hutool.core.collection.CollUtil;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author zhoujunlin
 * @date 2024年03月12日 14:27
 * @desc
 */
@Configuration
@ComponentScan
//  引入配置
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties({ServerProperties.class, WebMvcProperties.class})
public class WebConfig {

    /**
     * 内嵌web容器工厂
     */
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(ServerProperties serverProperties) {
        return new TomcatServletWebServerFactory(serverProperties.getPort());
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        // initStrategies 初始化逻辑
        return new DispatcherServlet();
    }

    /**
     * 注册DispatchServlet  springmvc入口
     */
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(
            DispatcherServlet dispatcherServlet,
            WebMvcProperties webMvcProperties
    ) {
        DispatcherServletRegistrationBean registrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        // 默认DispatcherServlet是在第一次访问应用时初始化的  这里优先级设置为1  让DispatcherServlet启动时初始化
        registrationBean.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
        return registrationBean;
    }

    /**
     * 如果使用DispatcherServlet.properties配置文件中的默认RequestMappingHandlerMapping，其不会作为spring的bean
     * 不方便后续测试  这里new一个RequestMappingHandlerMapping并存入spring容器  方便后续测试
     */
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    /**
     * 继承RequestMappingHandlerAdapter，并放入Bean容器  替换DispatcherServlet.properties配置中的默认RequestMappingHandlerAdapter
     */
    @Bean
    public MyRequestMappingHandlerAdapter myRequestMappingHandlerAdapter() {
        MyRequestMappingHandlerAdapter handlerAdapter = new MyRequestMappingHandlerAdapter();

        TokenArgumentResolver tokenArgumentResolver = new TokenArgumentResolver();
        handlerAdapter.setCustomArgumentResolvers(CollUtil.newArrayList(tokenArgumentResolver));

        YmlReturnValueHandler ymlReturnValueHandler = new YmlReturnValueHandler();
        handlerAdapter.setReturnValueHandlers(CollUtil.newArrayList(ymlReturnValueHandler));

        return handlerAdapter;
    }

}
