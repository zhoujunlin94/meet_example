package io.github.zhoujunlin94.example.web.extpoint.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author zhoujunlin
 * @date 2024年01月19日 14:38
 * @desc
 */
@Slf4j
public class DemoStartingApplicationListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        // SpringApplication.run.  listeners.starting
        System.err.println("001-DemoStartingApplicationListener===========>");
        log.warn("001-DemoStartingApplicationListener===========>");
    }

}
