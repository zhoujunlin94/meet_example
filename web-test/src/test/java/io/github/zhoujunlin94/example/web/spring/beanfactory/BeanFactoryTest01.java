package io.github.zhoujunlin94.example.web.spring.beanfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2024年02月21日 13:23
 * @desc
 */
public class BeanFactoryTest01 {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // Bean的定义  class  scope 初始化 销毁
        AbstractBeanDefinition configBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(BeanConfig.class).setScope(BeanDefinition.SCOPE_SINGLETON)
                .getBeanDefinition();
        beanFactory.registerBeanDefinition("beanConfig", configBeanDefinition);
        // 到这里 Bean容器中只有beanConfig一个bean  也就是说注解类的bean并未生效

        /**
         * 给BeanFactory添加后置处理器 例如:
         * internalConfigurationAnnotationProcessor(处理注解bean配置)
         * internalAutowiredAnnotationProcessor(处理@Autowired注入  优先级高于@Resource)
         * internalCommonAnnotationProcessor(处理@Resource注入)
         */
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        // 到这里 Bean容器中只有beanConfig以及上面的内置bean

        // 应用已经加入的BeanFactory后置处理器  补充一些Bean定义
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        // 到这里已经有了Bean1和Bean2了  但是注入类的注解这里还没有生效
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println("beanDefinitionName=====>" + beanDefinitionName);
        }

        // 给BeanFactory添加bean后置处理器 针对bean的各个生命周期进行扩展  例如@Autowired @Resource注解处理
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().stream()
                // 默认@Autowired优先级高  这里排序目的将@Resource优先级提在了@Autowired之前了
                //.sorted(AnnotationAwareOrderComparator.INSTANCE)
                .forEach(beanFactory::addBeanPostProcessor);
        // 此时@Autowired @Resource注解生效

        // 默认getBean的时候才会实例化Bean   这里可以调用此方法提前准备单例bean
        beanFactory.preInstantiateSingletons();

        if (beanFactory.containsBean("bean2")) {
            System.out.println("--------->");
            System.out.println(beanFactory.getBean(Bean2.class).getBean());
        }

        /**
         * 综上：
         * BeanFactory：
         * 不会主动注册并调用BeanFactory后置处理器
         * 不会主动注册Bean后置处理器   在BeanFactory初始化Bean的时候调用
         * 不会主动实例化Bean
         * 不会解析#{}  ${}
         */

    }

}

@Configuration
class BeanConfig {

    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean
    public Bean2 bean2() {
        return new Bean2();
    }

    @Bean
    public Bean3 bean3() {
        return new Bean3();
    }

}


class Bean1 implements BaseBean {

    public Bean1() {
        System.out.println("构造Bean1()");
    }

}

class Bean2 {

    @Autowired
    @Resource(name = "bean1")
    private BaseBean bean3;

    public Bean2() {
        System.out.println("构造Bean2()");
    }

    public BaseBean getBean() {
        return bean3;
    }
}

class Bean3 implements BaseBean {

    public Bean3() {
        System.out.println("构造Bean3()");
    }

}

interface BaseBean {
}
