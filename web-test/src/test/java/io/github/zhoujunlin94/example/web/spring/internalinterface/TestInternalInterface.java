package io.github.zhoujunlin94.example.web.spring.internalinterface;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author zhoujunlin
 * @date 2024/2/27 20:34
 * @desc Aware类接口主要是注入一些容器相关的信息
 * BeanFactoryAware  注入BeanFactory
 * BeanNameAware  注入Bean名字
 * ApplicationContextAware  注入ApplicationContext
 * @Autowired也可以注入这些对象 但是@Autowired生效需要BeanPostProcessor属于扩展功能  而这些接口属于内置功能
 * 在某些情况下@Autowired功能会失效
 */
public class TestInternalInterface {

    public static void main(String[] args) {
        //testInterface();

        testExt();
    }


    public static void testExt() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();

        applicationContext.registerBean("myBean", MyBean.class);

        /**
         * 在MyConfig中添加BeanFactoryProcessor导致@Autowired @Resource and @PostConstruct 失效
         */
        applicationContext.registerBean("myConfig", MyConfig.class);

        applicationContext.registerBean(ConfigurationClassPostProcessor.class);
        applicationContext.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        applicationContext.registerBean(CommonAnnotationBeanPostProcessor.class);

        applicationContext.refresh();
        applicationContext.close();
    }

    private static void testInterface() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();

        applicationContext.registerBean("myBean", MyBean.class);

        applicationContext.refresh();
        applicationContext.close();
    }


}
