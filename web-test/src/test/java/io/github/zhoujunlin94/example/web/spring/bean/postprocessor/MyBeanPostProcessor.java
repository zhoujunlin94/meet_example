package io.github.zhoujunlin94.example.web.spring.bean.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2023年05月07日 21:35
 * @desc
 */
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)) {
            System.err.println(">>>>>>> 销毁之前执行  如@PreDestroy");
        }
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)) {
            System.err.println(">>>>>>> 实例化之前执行，这里返回的bean会替换之前的bean");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)) {
            System.err.println(">>>>>>> 实例化之后执行，这里返回false 会跳过依赖注入阶段");
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)) {
            System.err.println(">>>>>>> 依赖注入阶段执行，如@Value  @Resource");
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)) {
            System.err.println(">>>>>>> 初始化之前执行，这里返回的对象会替换之前的bean，如@PostConstruct  @ConfigurationProperties");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("lifeCycleBean".equals(beanName)) {
            System.err.println(">>>>>>> 初始化之后执行，这里返回的对象会替换之前的bean  如代理增强");

//            Enhancer enhancer = new Enhancer();
//            enhancer.setSuperclass(bean.getClass());
//            enhancer.setCallback(new InvocationHandler() {
//                @Override
//                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
//                    System.err.println(">>>>>>> before proxy");
//                    Object invoke = method.invoke(bean, objects);
//                    System.err.println(">>>>>>> after proxy");
//                    return invoke;
//                }
//            });
//            return enhancer.create();
        }
        return bean;
    }

}
