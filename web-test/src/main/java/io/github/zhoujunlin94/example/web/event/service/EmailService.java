package io.github.zhoujunlin94.example.web.event.service;

import io.github.zhoujunlin94.example.web.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zhoujunlin
 * @date 2023年03月27日 16:39
 * @desc
 */
@Slf4j
@Service
public class EmailService implements ApplicationListener<UserRegisterEvent> {

    @Async
    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        log.info("EmailService.onApplicationEvent start...");
        int i = 1 / 0;
        log.info("[onApplicationEvent][给用户({}) 发送邮件]", event.getUserName());
        log.info("EmailService.onApplicationEvent end...");
    }

}
