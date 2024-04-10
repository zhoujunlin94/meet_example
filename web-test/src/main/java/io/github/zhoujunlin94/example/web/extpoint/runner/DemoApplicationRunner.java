package io.github.zhoujunlin94.example.web.extpoint.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhoujunlin
 * @date 2024年01月19日 14:53
 * @desc
 */
@Slf4j
@Component
public class DemoApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.warn("006-DemoApplicationRunner.run======>");
    }

}
