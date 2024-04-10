package io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor;

import lombok.SneakyThrows;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

/**
 * @author zhoujunlin
 * @date 2024/2/25 21:19
 * @desc
 */
public class MyMapperPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @SneakyThrows
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:io/github/zhoujunlin94/example/web/spring/beanfactorypostprocessor/mapper/**/*.class");
        CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        AnnotationBeanNameGenerator annotationBeanNameGenerator = new AnnotationBeanNameGenerator();
        for (Resource resource : resources) {
            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
            ClassMetadata classMetadata = metadataReader.getClassMetadata();
            if (classMetadata.isInterface()) {
                AbstractBeanDefinition mapperFactoryBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MapperFactoryBean.class)
                        .addConstructorArgValue(classMetadata.getClassName())
                        .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                        .getBeanDefinition();
                /**
                 * 这里BeanName不能使用mapperFactoryBeanDefinition
                 * 因为同名Bean会被覆盖
                 * 这个mapperBeanDefinition不放入Bean容器  只是用来生成Bean名字
                 */
                AbstractBeanDefinition mapperBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(classMetadata.getClassName()).getBeanDefinition();
                String beanName = annotationBeanNameGenerator.generateBeanName(mapperBeanDefinition, registry);

                registry.registerBeanDefinition(beanName, mapperFactoryBeanDefinition);
            }
        }

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
