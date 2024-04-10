package io.github.zhoujunlin94.example.web.spring.value;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author zhoujunlin
 * @date 2024/3/31 14:03
 * @desc
 */
@Configuration
public class TestValue {

    @SneakyThrows
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestValue.class);
        DefaultListableBeanFactory beanFactory = applicationContext.getDefaultListableBeanFactory();

        ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);

        //resolve(applicationContext, resolver, Bean1.class.getDeclaredField("javaHome"));
        //resolve(applicationContext, resolver, Bean1.class.getDeclaredField("age"));
        //resolve(applicationContext, resolver, Bean2.class.getDeclaredField("bean3"));
        resolve(applicationContext, resolver, Bean4.class.getDeclaredField("helloJavaHome"));

    }

    private static void resolve(AnnotationConfigApplicationContext applicationContext, ContextAnnotationAutowireCandidateResolver resolver, Field field) {
        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(field, true);

        // 获取@Value中的原始内容
        String originValue = resolver.getSuggestedValue(dependencyDescriptor).toString();
        System.out.println("originValue = " + originValue);
        // 解析并获取${}内的内容  字符串类型
        String resolvedPlaceholders = applicationContext.getEnvironment().resolvePlaceholders(originValue);
        System.out.println("resolvedPlaceholders = " + resolvedPlaceholders);

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        Object ret = beanFactory.getBeanExpressionResolver().evaluate(resolvedPlaceholders, new BeanExpressionContext(beanFactory, null));
        System.out.println("ret = " + ret);
        System.out.println("ret.getClass() = " + ret.getClass());

        // 类型转换
        ret = beanFactory.getTypeConverter().convertIfNecessary(ret, dependencyDescriptor.getDependencyType());
        System.out.println("ret = " + ret);
        System.out.println("ret.getClass() = " + ret.getClass());

    }


    static class Bean1 {

        @Value("${JAVA_HOME}")
        private String javaHome;

        @Value("30")
        private int age;

    }

    static class Bean2 {

        // #{SpEL}  springEL表达式  获取bean  @BeanName
        @Value("#{@bean3}")
        private Bean3 bean3;

    }

    @Component("bean3")
    static class Bean3 {

    }

    static class Bean4 {

        @Value("#{'hello, ' + '${JAVA_HOME}'}")
        private String helloJavaHome;

    }


}
