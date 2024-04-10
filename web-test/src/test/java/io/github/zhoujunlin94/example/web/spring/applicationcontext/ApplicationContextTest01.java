package io.github.zhoujunlin94.example.web.spring.applicationcontext;

import cn.hutool.core.date.DateUtil;
import io.github.zhoujunlin94.example.web.spring.applicationcontext.event.EventPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Locale;

/**
 * @author zhoujunlin
 * @date 2024年02月21日 14:08
 * @desc
 */
@SpringBootApplication
public class ApplicationContextTest01 {

    public static void main(String[] args) throws IOException {
        /**
         * ApplicationContext具备的能力：
         *  ResourcePatternResolver：路径处理
         *  EnvironmentCapable： 读取环境配置
         *  MessageSource：国际化
         *  ApplicationEventPublisher：事件发布
         */
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ApplicationContextTest01.class, args);

        /**
         * ResourcePatternResolver
         * classpath*:  可以加载jar包里的文件
         */
        Resource[] resources = applicationContext.getResources("classpath:application.yaml");
//        Resource[] resources = applicationContext.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.err.println(resource);
        }

        /**
         * EnvironmentCapable
         */
        String property = applicationContext.getEnvironment().getProperty("server.port");
        System.err.println(property);

        /**
         * MessageSource
         */
//        String welcome = applicationContext.getMessage("welcome", new String[]{"zhoujunlin", DateUtil.now()}, Locale.CHINA);
        String welcome = applicationContext.getMessage("welcome", new String[]{"zhoujunlin", DateUtil.now()}, Locale.US);
        System.err.println(welcome);

        /**
         * ApplicationEventPublisher
         */
        //applicationContext.publishEvent(new RegisterEvent(applicationContext));
        applicationContext.getBean(EventPublisher.class).publishEvent();
    }

}
