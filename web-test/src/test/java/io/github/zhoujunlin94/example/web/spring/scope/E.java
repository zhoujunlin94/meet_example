package io.github.zhoujunlin94.example.web.spring.scope;

import lombok.Getter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024年03月02日 16:58
 * @desc
 */
@Getter
@Component
public class E {

    @Autowired
    private F1 f1;

    @Lazy
    @Autowired
    private F2 f2;

    @Autowired
    private F3 f3;

    @Autowired
    private ObjectFactory<F4> f4;

    @Autowired
    private ApplicationContext applicationContext;

    public F1 getF12() {
        return applicationContext.getBean(F1.class);
    }

    public F4 getF4() {
        return f4.getObject();
    }

}
