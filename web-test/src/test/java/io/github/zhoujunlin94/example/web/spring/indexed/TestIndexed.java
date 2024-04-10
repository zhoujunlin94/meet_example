package io.github.zhoujunlin94.example.web.spring.indexed;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @author zhoujunlin
 * @date 2024/3/30 22:29
 * @desc a. @Indexed的原理，在编译的就根据@Indexed生成META-INF/spring.components文件
 * 扫描时
 * 1. 如果发现META-INF/spring.components存在，以它为准加载bean definition
 * 2. 否则，会遍历包下所有class资源 （包括jar内的）
 */
public class TestIndexed {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        ClassPathBeanDefinitionScanner beanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanFactory);
        beanDefinitionScanner.scan(TestIndexed.class.getPackage().getName());

        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

    }

}
