package io.github.zhoujunlin94.example.web.spring.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author zhoujunlin
 * @date 2024年02月29日 19:20
 * @desc
 */
@Scope("session")
@Component
public class Bean4Session {

    @PreDestroy
    public void destroy() {
        System.err.println("Bean4Session destroy");
    }

}
