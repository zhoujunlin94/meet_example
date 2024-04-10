package io.github.zhoujunlin94.example.web.spring.beanpostprocessor;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zhoujunlin
 * @date 2024/2/25 13:37
 * @desc
 */
public class DigInAutowiredBeanPostProcessor {

    public static void main(String[] args) throws Throwable {
        // 注册单例bean
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2", new Bean2());
        beanFactory.registerSingleton("bean3", new Bean3());
        // 处理@Value
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // ${}解析器
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        // 1. 查找哪些属性，方法添加了@Autowired注解   InjectionMetadata包装
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        autowiredAnnotationBeanPostProcessor.setBeanFactory(beanFactory);

        Bean1 bean1 = new Bean1();
        System.err.println("处理之前：" + bean1);
        // 2. 执行依赖注入 @Autowired @Value
//        autowiredAnnotationBeanPostProcessor.postProcessProperties(null, bean1, null);
//        System.err.println("处理之后："+bean1);

        Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
        findAutowiringMetadata.setAccessible(true);
        InjectionMetadata metadata = (InjectionMetadata) findAutowiringMetadata.invoke(autowiredAnnotationBeanPostProcessor, null, Bean1.class, null);

        // 2. 调用metadata进行依赖注入   注入时按照类型查找
        metadata.inject(bean1, null, null);
        System.err.println("处理之后：" + bean1);

        // 3. 如何按照类型查找值
        Field bean3Field = Bean1.class.getDeclaredField("bean3");
        DependencyDescriptor bean3DD = new DependencyDescriptor(bean3Field, false);
        Object bean3 = beanFactory.doResolveDependency(bean3DD, null, null, null);
        System.out.println(bean3);

        // 按照方法查找值
        Method setBean2Method = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor setBean2MethodDD = new DependencyDescriptor(new MethodParameter(setBean2Method, 0), false);
        Object bean2 = beanFactory.doResolveDependency(setBean2MethodDD, null, null, null);
        System.out.println(bean2);

        Method setHomeMethod = Bean1.class.getDeclaredMethod("setHome", String.class);
        DependencyDescriptor setHomeMethodDD = new DependencyDescriptor(new MethodParameter(setHomeMethod, 0), false);
        Object home = beanFactory.doResolveDependency(setHomeMethodDD, null, null, null);
        System.out.println(home);


        // beanFactory.autowireBeanProperties(bean, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);

    }

}
