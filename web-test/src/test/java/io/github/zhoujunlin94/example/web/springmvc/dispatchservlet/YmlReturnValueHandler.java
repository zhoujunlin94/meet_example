package io.github.zhoujunlin94.example.web.springmvc.dispatchservlet;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2024/3/14 22:31
 * @desc
 */
public class YmlReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        Yml yml = returnType.getMethodAnnotation(Yml.class);
        // 只有方法上存在Yml注解才解析
        return Objects.nonNull(yml);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // 1. 对象转yml
        String ret = new Yaml().dump(returnValue);

        // 2. 将结果写入响应
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().print(ret);

        // 3. 设置请求已处理完毕
        mavContainer.setRequestHandled(true);

    }
}
