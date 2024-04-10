package io.github.zhoujunlin94.example.web.springboot.autoconfiguration;

import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhoujunlin
 * @date 2024年03月29日 14:16
 * @desc
 */
public class TestAOPAutoConfiguration {

    public static void main(String[] args) {
        GenericApplicationContext gac = new GenericApplicationContext();
        // 配置文件
        StandardEnvironment standardEnvironment = new StandardEnvironment();
        // 添加命令行参数配置  关闭aop自动配置
        // standardEnvironment.getPropertySources().addLast(new SimpleCommandLinePropertySource("--spring.aop.auto=false"));
        gac.setEnvironment(standardEnvironment);
        // 添加后置处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(gac.getDefaultListableBeanFactory());
        gac.registerBean(Config.class);
        gac.refresh();

        for (String beanDefinitionName : gac.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

    }


    @Configuration
    @Import(MyImportSelector.class)
    static class Config {

    }

    static class MyImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{AopAutoConfiguration.class.getName()};
        }
    }

}
