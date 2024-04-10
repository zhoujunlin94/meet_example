package io.github.zhoujunlin94.example.web.spring.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024年03月02日 16:56
 * @desc
 */
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class F3 {


}
