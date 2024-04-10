package io.github.zhoujunlin94.example.web.spring.autowired;

import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2024/3/31 21:19
 * @desc
 */
@Configuration
public class TestAutowired02 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestAutowired02.class);
        DefaultListableBeanFactory beanFactory = applicationContext.getDefaultListableBeanFactory();

        //testArray(beanFactory);
        //testList(beanFactory);
        //testApplicationContext(beanFactory);
        //testGeneric(beanFactory);
        //testQualifier(beanFactory);
        //testPrimary(beanFactory);
        testName(beanFactory);
    }

    @SneakyThrows
    private static void testArray(DefaultListableBeanFactory beanFactory) {
        DependencyDescriptor serviceArr = new DependencyDescriptor(Target.class.getDeclaredField("serviceArr"), true);
        // 当前属性是一个数组
        //System.out.println(serviceArr.getDependencyType());
        if (serviceArr.getDependencyType().isArray()) {
            Class<?> componentType = serviceArr.getDependencyType().getComponentType();
            System.out.println("数组类的真实类型：" + componentType);
            // 根据类型从当前容器或者父级容器中获取bean名字
            String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, componentType);
            List<Object> retBeans = new ArrayList<>();
            for (String beanName : beanNames) {
                System.out.println("findBean=>" + beanName);
                // 从容器中根据beanName获取bean
                Object ret = serviceArr.resolveCandidate(beanName, componentType, beanFactory);
                retBeans.add(ret);
            }
            // 类型转换  list->数组
            Object ret = beanFactory.getTypeConverter().convertIfNecessary(retBeans, serviceArr.getDependencyType());
            System.out.println("最终注入的数组：" + ret);
        }
    }

    @SneakyThrows
    private static void testList(DefaultListableBeanFactory beanFactory) {
        DependencyDescriptor serviceList = new DependencyDescriptor(Target.class.getDeclaredField("serviceList"), true);
        if (serviceList.getDependencyType() == List.class) {
            Class<?> resolveType = serviceList.getResolvableType().getGeneric().resolve();
            System.out.println("list的真实类型：" + resolveType);
            String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, resolveType);
            List<Object> ret = new ArrayList<>();
            for (String beanName : beanNames) {
                Object bean = serviceList.resolveCandidate(beanName, resolveType, beanFactory);
                ret.add(bean);
            }
            System.out.println(ret);
        }
    }

    @SneakyThrows
    private static void testApplicationContext(DefaultListableBeanFactory beanFactory) {
        DependencyDescriptor applicationContext = new DependencyDescriptor(Target.class.getDeclaredField("applicationContext"), true);

        Field resolvableDependencies = DefaultListableBeanFactory.class.getDeclaredField("resolvableDependencies");
        resolvableDependencies.setAccessible(true);
        Map<Class<?>, Object> resolvableDependencyMap = (Map<Class<?>, Object>) resolvableDependencies.get(beanFactory);
        resolvableDependencyMap.forEach((k, v) -> {
            // 几个核心类没有放在一级缓存中，而是放在了resolvableDependencies成员变量中  分别是：ResourceLoader、ApplicationEventPublisher、ApplicationContext以及BeanFactory
            System.out.println("key: " + k + ", value:" + v);
        });


        for (Map.Entry<Class<?>, Object> entry : resolvableDependencyMap.entrySet()) {
            // 左边类型   =  右边类型    即 dependencyType能否复制给 Key
            if (entry.getKey().isAssignableFrom(applicationContext.getDependencyType())) {
                System.out.println("找到了applicationContext：" + entry.getValue());
                break;
            }
        }

    }

    @SneakyThrows
    private static void testGeneric(DefaultListableBeanFactory beanFactory) {
        DependencyDescriptor dao = new DependencyDescriptor(Target.class.getDeclaredField("dao"), true);
        Class<?> dependencyType = dao.getDependencyType();

        // @Value @Lazy 判断@Autowired所需要的类型是否与某个BeanDefinition匹配()
        ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);

        String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, dependencyType);
        for (String beanName : beanNames) {
            System.out.println("找到beanName:" + beanName);

            BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);
            if (resolver.isAutowireCandidate(new BeanDefinitionHolder(beanDefinition, beanName), dao)) {
                System.out.println("当前beanName:" + beanName + "匹配");
                System.out.println(dao.resolveCandidate(beanName, dependencyType, beanFactory));
            } else {
                System.out.println("当前beanName:" + beanName + "不匹配");
            }
        }

    }

    @SneakyThrows
    private static void testQualifier(DefaultListableBeanFactory beanFactory) {
        DependencyDescriptor service = new DependencyDescriptor(Target.class.getDeclaredField("service"), true);
        Class<?> dependencyType = service.getDependencyType();

        // @Value @Lazy 判断@Autowired所需要的类型是否与某个BeanDefinition匹配(范型、@Qualifier)
        ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);

        String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, dependencyType);
        for (String beanName : beanNames) {
            System.out.println("找到beanName:" + beanName);

            BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);
            if (resolver.isAutowireCandidate(new BeanDefinitionHolder(beanDefinition, beanName), service)) {
                System.out.println("当前beanName:" + beanName + "匹配");
                System.out.println(service.resolveCandidate(beanName, dependencyType, beanFactory));
            } else {
                System.out.println("当前beanName:" + beanName + "不匹配");
            }
        }

    }

    @SneakyThrows
    private static void testPrimary(DefaultListableBeanFactory beanFactory) {
        DependencyDescriptor service = new DependencyDescriptor(Target2.class.getDeclaredField("service"), true);
        Class<?> dependencyType = service.getDependencyType();

        String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, dependencyType);
        for (String beanName : beanNames) {
            System.out.println("找到beanName:" + beanName);

            BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);
            if (beanDefinition.isPrimary()) {
                System.out.println("当前beanName:" + beanName + "匹配");
                System.out.println(service.resolveCandidate(beanName, dependencyType, beanFactory));
            } else {
                System.out.println("当前beanName:" + beanName + "不匹配");
            }
        }

    }

    @SneakyThrows
    private static void testName(DefaultListableBeanFactory beanFactory) {
        DependencyDescriptor service3 = new DependencyDescriptor(Target2.class.getDeclaredField("service3"), true);
        Class<?> dependencyType = service3.getDependencyType();

        String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, dependencyType);
        for (String beanName : beanNames) {
            System.out.println("找到beanName:" + beanName);
            if (beanName.equals(service3.getDependencyName())) {
                System.out.println("当前beanName:" + beanName + "匹配");
                System.out.println(service3.resolveCandidate(beanName, dependencyType, beanFactory));
            } else {
                System.out.println("当前beanName:" + beanName + "不匹配");
            }
        }

    }


    static class Target {
        @Autowired
        private Service[] serviceArr;
        @Autowired
        private List<Service> serviceList;
        @Autowired
        private ConfigurableApplicationContext applicationContext;
        @Autowired
        private DAO<Teacher> dao;  // DAO2
        @Autowired
        @Qualifier("service2")
        private Service service;
    }

    static class Target2 {
        @Autowired
        private Service service;

        @Autowired
        private Service service3;
    }


    interface Service {
    }

    @Component("service1")
    static class Service1 implements Service {
    }

    @Primary
    @Component("service2")
    static class Service2 implements Service {
    }

    @Component("service3")
    static class Service3 implements Service {
    }

    interface DAO<T> {
    }

    static class Student {
    }

    static class Teacher {
    }

    @Component("DAO1")
    static class DAO1 implements DAO<Student> {
    }

    @Component("DAO2")
    static class DAO2 implements DAO<Teacher> {
    }

}
