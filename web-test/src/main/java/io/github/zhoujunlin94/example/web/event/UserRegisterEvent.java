package io.github.zhoujunlin94.example.web.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 16:34
 * @desc
 */
public class UserRegisterEvent extends ApplicationEvent {

    private String userName;

    public UserRegisterEvent(Object source) {
        super(source);
    }

    public UserRegisterEvent(Object source, String userName) {
        super(source);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
