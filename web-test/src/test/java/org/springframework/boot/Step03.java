package org.springframework.boot;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.EnvironmentPostProcessorApplicationListener;
import org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * @author zhoujunlin
 * @date 2024年03月28日 11:51
 * @desc 1. StandardEnvironment是spring中标准的环境类
 * 2. ApplicationEnvironment是springboot中的,但是它是default修饰的,所以必须在同一个包下测试,这里建了个org.springframework.boot包
 * <p>
 * systemProperties:  VM OPTIONS   -DJAVA_HOME=abc
 * systemEnvironment 系统环境配置
 */
public class Step03 {

    public static void main(String[] args) {
        test04();
    }

    @SneakyThrows
    private static void test04() {
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();
        applicationEnvironment.getPropertySources().addLast(new ResourcePropertySource(new ClassPathResource("application.properties")));
        applicationEnvironment.getPropertySources().addLast(new ResourcePropertySource(new ClassPathResource("step03.properties")));

        // 绑定新对象  user是前缀
        /*User user = Binder.get(applicationEnvironment).bind("user", User.class).get();
        System.out.println(user);*/

        // 绑定已有对象
        /*User user = new User();
        user.setFirstName("zzzz");
        Binder.get(applicationEnvironment).bind("user", Bindable.ofInstance(user));
        System.out.println(user);*/

        // 将spring.main开头的属性绑定给SpringApplication
        SpringApplication springApplication = new SpringApplication();
        System.out.println(springApplication);
        Binder.get(applicationEnvironment).bind("spring.main", Bindable.ofInstance(springApplication));
        // debug查看字段变更
        System.out.println(springApplication);
    }

    @Data
    static class User {
        private String firstName;
        private String middleName;
        private String lastName;
    }


    private static void test03(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        springApplication.addListeners(new EnvironmentPostProcessorApplicationListener());

        // 可以对环境进行增强的EnvironmentPostProcessor 执行时机是:EnvironmentPostProcessorApplicationListener事件监听
        /*List<String> environmentPostProcessorNames = SpringFactoriesLoader.loadFactoryNames(EnvironmentPostProcessor.class, Step04.class.getClassLoader());
        for (String environmentPostProcessorName : environmentPostProcessorNames) {
            System.out.println(environmentPostProcessorName);
        }*/

        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();
        System.out.println("=======>增强前");
        for (PropertySource<?> propertySource : applicationEnvironment.getPropertySources()) {
            System.out.println(propertySource);
        }
        // 触发事件 -> EnvironmentPostProcessorApplicationListener监听回调 -> 执行所有EnvironmentPostProcessor
        EventPublishingRunListener publisher = new EventPublishingRunListener(springApplication, args);
        publisher.environmentPrepared(new DefaultBootstrapContext(), applicationEnvironment);

        System.out.println("=======>增强后");
        for (PropertySource<?> propertySource : applicationEnvironment.getPropertySources()) {
            System.out.println(propertySource);
        }
    }

    private static void test02() {
        SpringApplication springApplication = new SpringApplication();
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();
        System.out.println("=======>增强前");
        for (PropertySource<?> propertySource : applicationEnvironment.getPropertySources()) {
            System.out.println(propertySource);
        }

        // 添加application.properties  application.yaml配置文件
        ConfigDataEnvironmentPostProcessor configDataEnvironmentPostProcessor = new ConfigDataEnvironmentPostProcessor(new DeferredLogs(), new DefaultBootstrapContext(), null);
        configDataEnvironmentPostProcessor.postProcessEnvironment(applicationEnvironment, springApplication);

        // 添加随机数 random.int  random.uuid   RandomValuePropertySource
        RandomValuePropertySourceEnvironmentPostProcessor randomValuePropertySourceEnvironmentPostProcessor = new RandomValuePropertySourceEnvironmentPostProcessor(new DeferredLog());
        randomValuePropertySourceEnvironmentPostProcessor.postProcessEnvironment(applicationEnvironment, springApplication);

        System.out.println("=======>增强后");
        for (PropertySource<?> propertySource : applicationEnvironment.getPropertySources()) {
            System.out.println(propertySource);
        }

        System.out.println(applicationEnvironment.getProperty("JAVA_HOME"));
        System.out.println(applicationEnvironment.getProperty("server.port"));


        System.out.println(applicationEnvironment.getProperty("random.int"));
        System.out.println(applicationEnvironment.getProperty("random.long"));
        System.out.println(applicationEnvironment.getProperty("random.uuid"));
    }

    @SneakyThrows
    private static void test01() {
        // 初始构造时加载了   系统属性(systemProperties  jvm属性配置)系统环境变量(systemEnvironment)
        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();
        // 放到最后  优先级最低
        applicationEnvironment.getPropertySources().addLast(new ResourcePropertySource(new ClassPathResource("application.properties")));
        applicationEnvironment.getPropertySources().addLast(new ResourcePropertySource(new ClassPathResource("step03.properties")));

        // 解决属性匹配  _ , - , 驼峰 均能匹配到数据
        ConfigurationPropertySources.attach(applicationEnvironment);

        for (PropertySource<?> propertySource : applicationEnvironment.getPropertySources()) {
            // 配置获取优先级
            System.out.println(propertySource);
        }

        System.out.println(applicationEnvironment.getProperty("JAVA_HOME"));
        System.out.println(applicationEnvironment.getProperty("server.port"));
        // 默认属性名完全一样才能获取到配置
        System.out.println(applicationEnvironment.getProperty("user.first-name"));
        System.out.println(applicationEnvironment.getProperty("user.middle-name"));
        System.out.println(applicationEnvironment.getProperty("user.last-name"));
    }

}
