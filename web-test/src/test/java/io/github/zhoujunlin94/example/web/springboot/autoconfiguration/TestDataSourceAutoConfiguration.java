package io.github.zhoujunlin94.example.web.springboot.autoconfiguration;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2024年03月29日 14:16
 * @desc
 */
public class TestDataSourceAutoConfiguration {

    public static void main(String[] args) {
        GenericApplicationContext gac = new GenericApplicationContext();
        // 配置文件
        StandardEnvironment standardEnvironment = new StandardEnvironment();
        // 添加命令行参数配置  添加数据源配置
        standardEnvironment.getPropertySources().addLast(new SimpleCommandLinePropertySource(
                "--spring.datasource.url=jdbc:mysql://localhost:3306/test",
                "--spring.datasource.username=root",
                "--spring.datasource.password=root"
        ));
        gac.setEnvironment(standardEnvironment);
        // 当前类所在包
        String packageName = TestDataSourceAutoConfiguration.class.getPackage().getName();
        // System.out.println("packageName = " + packageName);
        // 注册当前bean所在包   @AutoConfigurationPackage注解上的  @AutoConfigurationPackage
        AutoConfigurationPackages.register(gac.getDefaultListableBeanFactory(), packageName);
        // 添加后置处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(gac.getDefaultListableBeanFactory());
        gac.registerBean(Config.class);
        gac.refresh();

        for (String beanDefinitionName : gac.getBeanDefinitionNames()) {
            String resourceDescription = gac.getBeanDefinition(beanDefinitionName).getResourceDescription();
            if (Objects.nonNull(resourceDescription)) {
                System.out.println(beanDefinitionName + ", 来源为：=>" + resourceDescription);
            }
        }

        DataSourceProperties dataSourceProperties = gac.getBean(DataSourceProperties.class);
        System.out.println("dataSourceProperties.getUrl() = " + dataSourceProperties.getUrl());
        System.out.println("dataSourceProperties.getUsername() = " + dataSourceProperties.getUsername());
        System.out.println("dataSourceProperties.getPassword() = " + dataSourceProperties.getPassword());

    }


    @Configuration // 模拟本项目配置
    @Import(MyImportSelector.class)
    static class Config {

    }

    static class MyImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{
                    DataSourceAutoConfiguration.class.getName(),
                    MybatisAutoConfiguration.class.getName(),
                    DataSourceTransactionManagerAutoConfiguration.class.getName(),
                    TransactionAutoConfiguration.class.getName()
            };
        }
    }

}
