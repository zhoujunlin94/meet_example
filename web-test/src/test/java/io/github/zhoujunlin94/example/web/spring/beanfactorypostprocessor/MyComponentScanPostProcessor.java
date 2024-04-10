package io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2024/2/25 20:59
 * @desc
 */
public class MyComponentScanPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    @SneakyThrows
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //  正规写法应该获取所有@Configuration的类
        ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);

        if (Objects.nonNull(componentScan)) {
            CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
            BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
            PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
            for (String basePackage : componentScan.basePackages()) {
                String path = "classpath:" + basePackage.replace(".", "/") + "/**/*.class";
                for (Resource resource : pathMatchingResourcePatternResolver.getResources(path)) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                    if (annotationMetadata.hasAnnotation(Component.class.getName()) ||
                            annotationMetadata.hasMetaAnnotation(Component.class.getName())) {
                        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(metadataReader.getClassMetadata().getClassName())
                                .getBeanDefinition();
                        String beanName = beanNameGenerator.generateBeanName(beanDefinition, registry);
                        registry.registerBeanDefinition(beanName, beanDefinition);
                    }
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
