package io.github.zhoujunlin94.example.web.springmvc.returnvaluehandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * @author zhoujunlin
 * @date 2024年03月22日 10:26
 * @desc
 */
@Configuration
public class WebConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");
        return freeMarkerConfigurer;
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver(FreeMarkerConfigurer freeMarkerConfigurer) {
        // Spring初始化FreeMarkerView时,会要求Web环境  这里去掉此制约
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver() {
            public AbstractUrlBasedView instantiateView() {
                FreeMarkerView freeMarkerView = new FreeMarkerView() {
                    @Override
                    protected boolean isContextRequired() {
                        return false;
                    }
                };
                freeMarkerView.setConfiguration(freeMarkerConfigurer.getConfiguration());
                return freeMarkerView;
            }
        };
        freeMarkerViewResolver.setContentType("text/html;charset=utf-8");
        freeMarkerViewResolver.setPrefix("/");
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setExposeSpringMacroHelpers(false);
        return freeMarkerViewResolver;
    }

}
