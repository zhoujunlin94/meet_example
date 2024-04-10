package io.github.zhoujunlin94.example.web.extpoint.initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhoujunlin
 * @date 2024年01月19日 14:28
 * @desc
 */
@Slf4j
public class DemoApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // SpringApplication.prepareContext.applyInitializers调用
        log.warn("002-DemoApplicationContextInitializer===========>");
    }

}
