package io.github.zhoujunlin94.example.web.spring.beanpostprocessor;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author zhoujunlin
 * @date 2024/2/25 13:19
 * @desc
 */
public class BeanPostProcessorTest01 {

    public static void main(String[] args) {
        // GenericApplicationContext 是一个很干净的容器   无任何后处理器
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext();

        // 用原始方法注册三个bean
        genericApplicationContext.registerBean("bean1", Bean1.class);
        genericApplicationContext.registerBean("bean2", Bean2.class);
        genericApplicationContext.registerBean("bean3", Bean3.class);
        genericApplicationContext.registerBean("bean4", Bean4.class);

        // ContextAnnotationAutowireCandidateResolver 获取@Value的值
        genericApplicationContext.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // @Autowired  @Value的bean处理器
        genericApplicationContext.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // @Resource  @PostConstruct @PreDestroy处理器
        genericApplicationContext.registerBean(CommonAnnotationBeanPostProcessor.class);

        // 处理@ConfigurationProperties注解
        ConfigurationPropertiesBindingPostProcessor.register(genericApplicationContext.getDefaultListableBeanFactory());

        // 初始化容器 即执行BeanFactory后置处理器  添加Bean后置处理器  初始化所有单例
        genericApplicationContext.refresh();

        System.err.println(genericApplicationContext.getBean(Bean4.class));

        // 销毁容器
        genericApplicationContext.close();
    }

}
