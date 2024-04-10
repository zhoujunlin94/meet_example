package io.github.zhoujunlin94.example.web.extpoint.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author zhoujunlin
 * @date 2024年01月19日 14:40
 * @desc
 */
@Slf4j
public class DemoStartedApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.warn("005-DemoStartedApplicationListener=======>");
    }
}
