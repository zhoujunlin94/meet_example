package io.github.zhoujunlin94.example.web.event.service;

import io.github.zhoujunlin94.example.web.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 16:37
 * @desc
 */
@Slf4j
@Service
public class UserService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void register(String userName) {
        // ... 执行注册逻辑
        log.info("[register][执行用户({}) 的注册逻辑]", userName);

        // <2> ... 发布
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, userName));
    }

}
