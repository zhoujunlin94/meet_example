package io.github.zhoujunlin94.example.web.spring.factorybean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024/3/30 22:10
 * @desc
 */
@Slf4j
@Component("bean1")
public class Bean1FactoryBean implements FactoryBean<Bean1> {

    @Override
    public Bean1 getObject() throws Exception {
        Bean1 bean1 = new Bean1();
        log.warn("createBean1:{}", bean1);
        return bean1;
    }

    @Override
    public Class<?> getObjectType() {
        // applicationContext.getBean(Bean1.class) 需要此方法返回类型  否则return null就会报错
        // return null;
        return Bean1.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
