package io.github.zhoujunlin94.example.web.spring.autowired;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author zhoujunlin
 * @date 2024/3/31 21:01
 * @desc
 */
@Configuration
public class TestAutowired {

    @SneakyThrows
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestAutowired.class);
        DefaultListableBeanFactory beanFactory = applicationContext.getDefaultListableBeanFactory();

        // 1. 根据成员变量类型注入
        DependencyDescriptor dd1 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"), true);
        System.out.println(beanFactory.doResolveDependency(dd1, "bean1", null, null));

        // 2. 根据方法的入参类型注入   方法名 + 参数索引
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(Bean1.class.getMethod("setBean2", Bean2.class), 0), true);
        System.out.println(beanFactory.doResolveDependency(dd2, "bean1", null, null));

        // 3. Optional成员变量注入
        DependencyDescriptor dd3 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean3"), true);
        System.out.println("bean3类型：" + dd3.getDependencyType());
        if (dd3.getDependencyType() == Optional.class) {
            // 内嵌级别+1 获取真实类型
            dd3.increaseNestingLevel();
            System.out.println("内嵌级别+1后：" + dd3.getDependencyType());
            Object ret = beanFactory.doResolveDependency(dd3, "bean1", null, null);
            System.out.println(Optional.ofNullable(ret));
        }

        // 4. ObjectFactory成员变量注入
        DependencyDescriptor dd4 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean4"), true);
        System.out.println("bean4类型：" + dd4.getDependencyType());
        if (dd4.getDependencyType() == ObjectFactory.class) {
            // 内嵌级别+1 获取真实类型
            dd4.increaseNestingLevel();
            System.out.println("内嵌级别+1后：" + dd4.getDependencyType());
            Object ret = beanFactory.doResolveDependency(dd4, "bean1", null, null);
            ObjectFactory objectFactory = new ObjectFactory() {
                @Override
                public Object getObject() throws BeansException {
                    return ret;
                }
            };
            System.out.println(objectFactory.getObject());
        }

        // 5. @Lazy处理
        DependencyDescriptor dd5 = new DependencyDescriptor(Bean1.class.getDeclaredField("bean5"), true);
        // 解析@Value、@Lazy
        ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);

        Object proxy = resolver.getLazyResolutionProxyIfNecessary(dd5, "bean1");
        System.out.println(proxy);
        // 生成的对象是动态代理
        System.out.println(proxy.getClass());

    }


    static class Bean1 {

        @Autowired
        private Bean2 bean2;

        @Autowired
        public void setBean2(Bean2 bean2) {
            this.bean2 = bean2;
        }

        @Autowired
        private Optional<Bean2> bean3;

        @Autowired
        private ObjectFactory<Bean2> bean4;

        @Autowired
        @Lazy
        private Bean2 bean5;

    }


    @Component("bean2")
    static class Bean2 {
    }

}
