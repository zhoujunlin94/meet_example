package io.github.zhoujunlin94.example.web.springboot.main;

import lombok.SneakyThrows;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.*;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

/**
 * @author zhoujunlin
 * @date 2024年03月28日 11:01
 * @desc ProgramArguments:
 * --server.port=8080 debug
 */
public class Step02 {

    @SneakyThrows
    @SuppressWarnings("all")
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        springApplication.addInitializers((applicationContext) -> {
            System.out.println("\t执行初始化器回调增强容器");
        });

        System.out.println("8. 创建容器");
        GenericApplicationContext applicationContext = createApplicationContext(WebApplicationType.SERVLET);

        System.out.println("9. 准备容器");
        for (ApplicationContextInitializer initializer : springApplication.getInitializers()) {
            initializer.initialize(applicationContext);
        }

        System.out.println("10. 加载BeanDefinition");
        // 注解配置类中读取Bean
        DefaultListableBeanFactory beanFactory = applicationContext.getDefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        annotatedBeanDefinitionReader.registerBean(Config.class);
        // 从xml中读取
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("b02.xml"));
        // 扫包获取
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanFactory);
        classPathBeanDefinitionScanner.scan("io.github.zhoujunlin94.example.web.springboot.sub");

        System.out.println("11. refresh容器");
        applicationContext.refresh();

        System.out.println("12. 执行runner");
        for (CommandLineRunner commandLineRunner : applicationContext.getBeansOfType(CommandLineRunner.class).values()) {
            commandLineRunner.run(args);
        }
        DefaultApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        for (ApplicationRunner applicationRunner : applicationContext.getBeansOfType(ApplicationRunner.class).values()) {
            applicationRunner.run(applicationArguments);
        }

        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println("beanName:" + beanDefinitionName + " 来源=>" + applicationContext.getBeanFactory().getBeanDefinition(beanDefinitionName).getResourceDescription());
        }
        applicationContext.close();
    }

    private static GenericApplicationContext createApplicationContext(WebApplicationType webApplicationType) {
        GenericApplicationContext applicationContext = null;
        switch (webApplicationType) {
            case SERVLET:
                applicationContext = new AnnotationConfigServletWebServerApplicationContext();
                break;
            case REACTIVE:
                applicationContext = new AnnotationConfigReactiveWebServerApplicationContext();
                break;
            case NONE:
                applicationContext = new AnnotationConfigApplicationContext();
                break;
            default:
                break;
        }
        return applicationContext;
    }

    static class Bean4 {
    }

    static class Bean5 {
    }

    static class Bean6 {
    }

    @Configuration
    static class Config {

        @Bean
        public Bean5 bean5() {
            return new Bean5();
        }

        @Bean
        public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public CommandLineRunner commandLineRunner() {
            return new CommandLineRunner() {
                @Override
                public void run(String... args) throws Exception {
                    System.out.println("\tCommandLineRunner..." + Arrays.toString(args));
                }
            };
        }

        @Bean
        public ApplicationRunner applicationRunner() {
            return new ApplicationRunner() {
                @Override
                public void run(ApplicationArguments args) throws Exception {
                    System.out.println("\tApplicationRunner..." + Arrays.toString(args.getSourceArgs()));
                    System.out.println("\tNonOptionArgs:" + args.getNonOptionArgs());
                    System.out.println("\tOptionNames:" + args.getOptionNames());
                    System.out.println("\tOptionArg:server.port: " + args.getOptionValues("server.port"));
                }
            };
        }

    }

}
