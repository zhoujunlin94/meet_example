package io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * @author zhoujunlin
 * @date 2024/2/25 20:26
 * @desc
 */
public class BeanFactoryPostProcessorTest01 {

    public static void main(String[] args) throws IOException {
        // testConfigurationClassPostProcessor();

        // ConfigurationClassPostProcessor内部是如何完成@ComponentScan的
        // digInComponentScan();
        // testMyComponentScanPostProcessor();

        // ConfigurationClassPostProcessor内部是如何完成@Bean的
        //digInAtBean();
        //testMyAtBeanPostProcessor();

        // 测试MapperFactoryBean
        //testMapperFactoryBean();
        testMyMapperBeanPostProcessor();
    }

    private static void testMyMapperBeanPostProcessor() {
        // GenericApplicationContext无BeanFactory后置处理器
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean("config", Config.class);
        applicationContext.registerBean(ConfigurationClassPostProcessor.class);

        applicationContext.registerBean(MyMapperPostProcessor.class);

        applicationContext.refresh();
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        applicationContext.close();

    }


    private static void testMapperFactoryBean() {
        // GenericApplicationContext无BeanFactory后置处理器
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean("config", Config.class);

        applicationContext.registerBean(ConfigurationClassPostProcessor.class);

        applicationContext.refresh();
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        applicationContext.close();

    }

    private static void testMyAtBeanPostProcessor() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean("config", Config.class);

        applicationContext.registerBean(MyAtBeanPostProcessor.class);

        applicationContext.refresh();

        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        System.out.println(applicationContext.getBean(SqlSessionFactoryBean.class));
    }

    @SneakyThrows
    private static void digInAtBean() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean("config", Config.class);

        CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(new ClassPathResource("io/github/zhoujunlin94/example/web/spring/beanfactorypostprocessor/Config.class"));
        Set<MethodMetadata> methods = metadataReader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
        for (MethodMetadata method : methods) {
            String initMethod = method.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();
//            System.out.println(method);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition()
                    .setFactoryMethodOnBean(method.getMethodName(), "config")
                    // 方法参数值注入
                    .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
            if (StrUtil.isNotBlank(initMethod)) {
                beanDefinitionBuilder.setInitMethodName(initMethod);
            }
            // 一般@Bean的bean名字都是取方法名
            applicationContext.getDefaultListableBeanFactory().registerBeanDefinition(method.getMethodName(), beanDefinitionBuilder.getBeanDefinition());
        }

        applicationContext.refresh();

        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        System.out.println(applicationContext.getBean(SqlSessionFactoryBean.class));

    }

    private static void testMyComponentScanPostProcessor() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean("config", Config.class);

        applicationContext.registerBean(MyComponentScanPostProcessor.class);

        applicationContext.refresh();

        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        applicationContext.close();
    }

    private static void digInComponentScan() throws IOException {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean("config", Config.class);

        ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);

        if (Objects.nonNull(componentScan)) {
            CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
            BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
            DefaultListableBeanFactory defaultListableBeanFactory = applicationContext.getDefaultListableBeanFactory();
            for (String basePackage : componentScan.basePackages()) {
                //System.out.println(basePackage);
                // io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor.component  -> classpath:io/github/zhoujunlin94/example/web/spring/beanfactorypostprocessor/component/**/*.class
                String path = "classpath:" + basePackage.replace(".", "/") + "/**/*.class";
                //System.out.println(path);
                for (Resource resource : applicationContext.getResources(path)) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    /*System.out.println("类名："+ metadataReader.getClassMetadata().getClassName());
                    System.out.println("是否有@Componenet注解:"+metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName()));
                    System.out.println("是否有@Componenet派生注解:"+metadataReader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName()));*/
                    AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                    if (annotationMetadata.hasAnnotation(Component.class.getName()) ||
                            annotationMetadata.hasMetaAnnotation(Component.class.getName())) {
                        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(metadataReader.getClassMetadata().getClassName())
                                .getBeanDefinition();
                        String beanName = beanNameGenerator.generateBeanName(beanDefinition, defaultListableBeanFactory);
                        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
                    }

                }
            }
        }

        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

    }


    private static void testConfigurationClassPostProcessor() {
        // GenericApplicationContext无BeanFactory后置处理器
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean("config", Config.class);
        /**
         * 处理@ComponentScan @Bean  @Import @ImportResource 的后置处理器
         * 不加此处理器  当前Context中只有config这一个bean
         */
        applicationContext.registerBean(ConfigurationClassPostProcessor.class);

        // @MapperScan功能
        applicationContext.registerBean(MapperScannerConfigurer.class, beanDefinition -> {
            beanDefinition.getPropertyValues().add("basePackage", "io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor.component.mapper");
        });

        applicationContext.refresh();
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        applicationContext.close();
    }


}
