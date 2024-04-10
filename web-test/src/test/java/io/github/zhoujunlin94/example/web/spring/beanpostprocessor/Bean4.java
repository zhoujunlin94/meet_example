package io.github.zhoujunlin94.example.web.spring.beanpostprocessor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhoujunlin
 * @date 2024/2/25 13:33
 * @desc
 */
@Data
@ConfigurationProperties(prefix = "java")
public class Bean4 {

    private String home;
    private String version;

}
