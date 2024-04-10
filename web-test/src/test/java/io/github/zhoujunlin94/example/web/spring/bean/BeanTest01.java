package io.github.zhoujunlin94.example.web.spring.bean;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2024/2/24 21:35
 * @desc
 */
@SpringBootApplication
public class BeanTest01 {

    public static void main(String[] args) throws Exception {

        testAnnotation();

        // 容器销毁  bean销毁
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(BeanTest01.class, args);
//        applicationContext.close();


    }


    private static void testAnnotation() throws Exception {
        Class<LifeCycleBean> lifeCycleBeanClass = LifeCycleBean.class;
        Component annotation = lifeCycleBeanClass.getAnnotation(Component.class);
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
        Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
        //  打开权限
        memberValues.setAccessible(true);
        Map<String, Object> values = (Map<String, Object>) memberValues.get(invocationHandler);
        values.put("value", "lifeCycleBeanReWrite");

    }

}
