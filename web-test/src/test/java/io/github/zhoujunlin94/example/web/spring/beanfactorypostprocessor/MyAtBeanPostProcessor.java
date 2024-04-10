package io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.Set;

/**
 * @author zhoujunlin
 * @date 2024/2/25 21:19
 * @desc
 */
public class MyAtBeanPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @SneakyThrows
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
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
            registry.registerBeanDefinition(method.getMethodName(), beanDefinitionBuilder.getBeanDefinition());
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
