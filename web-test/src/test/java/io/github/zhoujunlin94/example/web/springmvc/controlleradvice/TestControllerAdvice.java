package io.github.zhoujunlin94.example.web.springmvc.controlleradvice;

import cn.hutool.core.collection.CollUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * @author zhoujunlin
 * @date 2024/3/24 20:54
 * @desc
 */
public class TestControllerAdvice {

    @SneakyThrows
    public static void main(String[] args) {
        // 1. 准备容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebConfig.class);

        // 2. 准备请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);

        // 3. 准备servletInvocableHandlerMethod
        ServletInvocableHandlerMethod servletInvocableHandlerMethod = new ServletInvocableHandlerMethod(
                applicationContext.getBean(WebConfig.MyController.class),
                WebConfig.MyController.class.getDeclaredMethod("user"));
        // 这里没有特殊参数绑定  入参传null
        ServletRequestDataBinderFactory servletRequestDataBinderFactory = new ServletRequestDataBinderFactory(Collections.emptyList(), null);
        servletInvocableHandlerMethod.setDataBinderFactory(servletRequestDataBinderFactory);
        servletInvocableHandlerMethod.setParameterNameDiscoverer(new DefaultParameterNameDiscoverer());
        servletInvocableHandlerMethod.setHandlerMethodArgumentResolvers(argumentResolverComposite(applicationContext));
        servletInvocableHandlerMethod.setHandlerMethodReturnValueHandlers(returnValueHandlerComposite(applicationContext));

        // 4. 准备ModelAndViewContainer容器
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();

        // 5. 方法执行
        servletInvocableHandlerMethod.invokeAndHandle(
                servletWebRequest,
                modelAndViewContainer);

        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

        // 8. 关闭容器
        applicationContext.close();

    }


    private static HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite(ApplicationContext applicationContext) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        HandlerMethodReturnValueHandlerComposite returnValueHandlerComposite = new HandlerMethodReturnValueHandlerComposite();
        returnValueHandlerComposite.addHandlers(CollUtil.newArrayList(
                // 返回值：ModelAndView
                new ModelAndViewMethodReturnValueHandler(),
                // 返回值字符串：视图名
                new ViewNameMethodReturnValueHandler(),
                // 返回模型数据  带@ModelAttribute注解
                new ServletModelAttributeMethodProcessor(false),
                // 返回值：HttpEntity
                new HttpEntityMethodProcessor(CollUtil.newArrayList(jackson2HttpMessageConverter)),
                // 返回值：HttpHeaders
                new HttpHeadersReturnValueHandler(),
                // 返回值json: 带@ResponseBody注解     添加自定义ControllerAdvice
                new RequestResponseBodyMethodProcessor(CollUtil.newArrayList(jackson2HttpMessageConverter),
                        CollUtil.newArrayList(applicationContext.getBean(WebConfig.MyControllerAdvice.class))),
                // 返回模型数据  不带@ModelAttribute注解
                new ServletModelAttributeMethodProcessor(true)

        ));
        return returnValueHandlerComposite;
    }

    private static HandlerMethodArgumentResolverComposite argumentResolverComposite(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        DefaultListableBeanFactory beanFactory = annotationConfigApplicationContext.getDefaultListableBeanFactory();
        HandlerMethodArgumentResolverComposite resolverComposite = new HandlerMethodArgumentResolverComposite();
        resolverComposite.addResolvers(
                // @RequestParam参数解析器       false表示参数上必须有注解才会解析
                new RequestParamMethodArgumentResolver(beanFactory, false),
                // @PathVariable
                new PathVariableMethodArgumentResolver(),
                // @RequestHeader
                new RequestHeaderMethodArgumentResolver(beanFactory),
                // @CookieValue
                new ServletCookieValueMethodArgumentResolver(beanFactory),
                // Spring表达式参数解析器
                new ExpressionValueMethodArgumentResolver(beanFactory),
                // HttpServletRequest
                new ServletRequestMethodArgumentResolver(),
                // 是否不需要注解@ModelAttribute false：需要  即没有@ModelAttribute的实体参数不解析
                new ServletModelAttributeMethodProcessor(false),
                // @RequestBody  @ResponseBody
                new RequestResponseBodyMethodProcessor(CollUtil.newArrayList(new MappingJackson2HttpMessageConverter())),
                /**
                 *是否不需要注解@ModelAttribute true：不需要  即没有@ModelAttribute的实体参数也解析
                 * 注意！！！  这个解析器必须放在@RequestBody解析器后面，否则这个解析器将会优先被使用去解析
                 */
                new ServletModelAttributeMethodProcessor(true),
                /**
                 * @RequestParam 参数解析器 true表示参数上没有注解也会解析
                 * 注意！！！  这个解析器必须放在后面 否则其它类型参数将使用这个解析器解析
                 */
                new RequestParamMethodArgumentResolver(beanFactory, true)
        );
        return resolverComposite;
    }


}
