package io.github.zhoujunlin94.example.web.spring.applicationcontext.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024年02月21日 15:54
 * @desc
 */
@Component
public class EventConsumer {

    @EventListener
    public void handler(RegisterEvent registerEvent) {
        System.out.println("RegisterEvent by EventHandler1 source:" + registerEvent.getSource());
    }

}
