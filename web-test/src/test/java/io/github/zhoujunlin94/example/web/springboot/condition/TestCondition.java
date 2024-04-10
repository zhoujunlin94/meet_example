package io.github.zhoujunlin94.example.web.springboot.condition;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2024年03月29日 14:16
 * @desc
 */
public class TestCondition {

    public static void main(String[] args) {
        // 不带任务处理器的容器
        GenericApplicationContext gac = new GenericApplicationContext();
        // 默认允许bean覆盖 这里改为不允许
        gac.setAllowBeanDefinitionOverriding(false);
        gac.registerBean(Config.class);
        gac.registerBean(ConfigurationClassPostProcessor.class);
        gac.refresh();

        for (String beanDefinitionName : gac.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

    }


    @Configuration
    @Import(MyImportSelector.class)
    static class Config {

        @Bean
        public Bean1 bean1() {
            return new Bean1().setName("本项目");
        }

    }

    static class MyImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{AutoConfiguration1.class.getName(), AutoConfiguration2.class.getName()};
        }
    }

    static class Condition1 implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            // 存在DruidDataSource依赖
            return ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource", null);
        }
    }

    static class Condition2 implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            // 不存在DruidDataSource依赖
            return !ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource", null);
        }
    }

    static class Condition3 implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> attributes = metadata.getAnnotationAttributes(MyConditional.class.getName());
            String className = (String) attributes.get("className");
            boolean exist = (boolean) attributes.get("exist");
            boolean present = ClassUtils.isPresent(className, null);
            return exist == present;
        }
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Conditional(Condition3.class)
    @interface MyConditional {
        // 是否存在
        boolean exist() default true;

        // 类名
        String className();
    }

    @Configuration
    //@Conditional(Condition1.class)
    @MyConditional(className = "com.alibaba.druid.pool.DruidDataSource", exist = false)
    static class AutoConfiguration1 {

        @Bean
        @ConditionalOnMissingBean
        public Bean1 bean1() {
            return new Bean1().setName("第三方");
        }

    }

    @Configuration
    //@Conditional(Condition2.class)
    @MyConditional(className = "com.alibaba.druid.pool.DruidDataSource", exist = true)
    static class AutoConfiguration2 {

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

    }

    @Data
    static class Bean1 {
        private String name;
    }

    static class Bean2 {
    }

}
