package io.github.zhoujunlin94.example.web.springmvc.dispatchservlet;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhoujunlin
 * @date 2024/3/14 22:12
 * @desc 继承RequestMappingHandlerAdapter，重新invokeHandlerMethod方法
 * 因为此方法是protected的，外部无法调用  继承后修改为public便于测试
 */
public class MyRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    @Override
    public ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        return super.invokeHandlerMethod(request, response, handlerMethod);
    }
}
