package io.github.zhoujunlin94.example.web.spring.applicationcontext.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhoujunlin
 * @date 2024年02月21日 16:02
 * @desc
 */
public class RegisterEvent extends ApplicationEvent {

    public RegisterEvent(Object source) {
        super(source);
    }
}
