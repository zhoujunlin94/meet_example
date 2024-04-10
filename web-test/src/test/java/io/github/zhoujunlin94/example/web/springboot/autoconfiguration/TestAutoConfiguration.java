package io.github.zhoujunlin94.example.web.springboot.autoconfiguration;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * @author zhoujunlin
 * @date 2024年03月29日 14:16
 * @desc
 */
public class TestAutoConfiguration {

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

        Bean1 bean = gac.getBean(Bean1.class);
        System.out.println("bean1来自:" + bean.getName());

    }


    @Configuration // 模拟本项目配置
    //@Import({AutoConfiguration1.class, AutoConfiguration2.class})
    @Import(MyImportSelector.class)
    static class Config {

        @Bean
        public Bean1 bean1() {
            // 模拟本地项目与第三方项目存在同名同类型的bean
            return new Bean1().setName("本项目");
        }

    }

    static class MyImportSelector
            // implements ImportSelector
            // ImportSelector 默认先加载这里提供的bean   DeferredImportSelector表示延迟加载这里提供的bean
            implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            // 写死
            //return new String[]{AutoConfiguration1.class.getName(), AutoConfiguration2.class.getName()};
            System.out.println("==============>");
            // META-INF/spring.factories配置文件下面所有自动配置类 EnableAutoConfiguration
            for (String name : SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, null)) {
                System.out.println("-------->" + name);
            }
            System.out.println("==============>");
            // 配置文件读取
            List<String> classNames = SpringFactoriesLoader.loadFactoryNames(MyImportSelector.class, null);
            return classNames.toArray(new String[0]);
        }
    }

    @Configuration  // 模拟第三方jar包配置
    static class AutoConfiguration1 {

        @Bean
        // 现加载本项目的  没有的话 才加载当前配置
        @ConditionalOnMissingBean
        public Bean1 bean1() {
            return new Bean1().setName("第三方");
        }

    }

    @Configuration   // 模拟第三方jar包配置
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
