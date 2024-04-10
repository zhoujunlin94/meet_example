package io.github.zhoujunlin94.example.web.springmvc.dispatchservlet;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2024/3/14 22:23
 * @desc 自定义Token注解对应的入参解析器
 */
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 只有入参中有Token注解才支持解析
        Token token = parameter.getParameterAnnotation(Token.class);
        return Objects.nonNull(token);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return webRequest.getHeader("token");
    }

}
