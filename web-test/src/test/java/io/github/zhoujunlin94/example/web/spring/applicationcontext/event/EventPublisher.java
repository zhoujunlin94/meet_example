package io.github.zhoujunlin94.example.web.spring.applicationcontext.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2024年02月21日 15:54
 * @desc
 */
@Component
public class EventPublisher {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent() {
        applicationEventPublisher.publishEvent(new RegisterEvent(this));
    }

}
