package io.github.zhoujunlin94.example.web.spring.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author zhoujunlin
 * @date 2024年02月29日 19:20
 * @desc
 */
@Scope("request")
@Component
public class Bean4Request {

    @PreDestroy
    public void destroy() {
        // request请求结束立即销毁
        System.err.println("Bean4Request destroy");
    }

}
