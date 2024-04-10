package io.github.zhoujunlin94.example.web.springboot.main;

import cn.hutool.core.collection.CollUtil;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Method;

/**
 * @author zhoujunlin
 * @date 2024年03月28日 9:57
 * @desc
 */
@Configuration
public class TestSpringApplication {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("1. 从源中获取BeanDefinition");
        // 配置类中获取
        SpringApplication springApplication = new SpringApplication(TestSpringApplication.class);
        // xml中获取
        springApplication.setSources(CollUtil.newHashSet(
                "classpath:b01.xml"
        ));

        System.out.println("2. 推断应用类型");
        Method deduceFromClasspath = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
        deduceFromClasspath.setAccessible(true);
        // 静态方法  不需要对象
        System.out.println("\t应用类型:" + deduceFromClasspath.invoke(null));

        System.out.println("3. 添加初始化器");
        springApplication.addInitializers(applicationContext -> {
            if (applicationContext instanceof GenericApplicationContext) {
                GenericApplicationContext gac = (GenericApplicationContext) applicationContext;
                // 这个初始化器被调用时  会注册一个bean
                gac.registerBean("bean3", Bean3.class);
            }
        });

        System.out.println("4. 添加监听器");
        springApplication.addListeners(event -> {
            System.out.println("\t事件为:" + event.getClass());
        });

        System.out.println("5. 主类推断");
        Method deduceMainApplicationClass = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
        deduceMainApplicationClass.setAccessible(true);
        System.out.println("\t主类为:" + deduceMainApplicationClass.invoke(springApplication));

        ConfigurableApplicationContext applicationContext = springApplication.run(args);
        // 创建ApplicationContext
        // 调用初始化器对ApplicationContext做扩展
        // ApplicationContext.refresh


        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println("beanName:" + beanDefinitionName + " 来源=>" + applicationContext.getBeanFactory().getBeanDefinition(beanDefinitionName).getResourceDescription());
        }
        applicationContext.close();


    }


    static class Bean1 {
    }

    static class Bean2 {
    }

    static class Bean3 {
    }

    @Bean
    public Bean2 bean2() {
        return new Bean2();
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

}

