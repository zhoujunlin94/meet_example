package io.github.zhoujunlin94.example.web.springmvc.exphandler;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2024/3/24 22:06
 * @desc
 */
@Configuration
public class WebConfig {

    @ControllerAdvice
    public static class MyControllerAdvice {
        @ExceptionHandler
        @ResponseBody
        public Map<String, Object> handle(Exception e) {
            System.out.println("全局异常处理...");
            // 全局异常处理器-json
            // 局部异常处理器优先于全局异常处理器
            return ImmutableMap.of("error", e.getMessage());
        }
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(CollUtil.newArrayList(mappingJackson2HttpMessageConverter));
        return resolver;
    }


}
