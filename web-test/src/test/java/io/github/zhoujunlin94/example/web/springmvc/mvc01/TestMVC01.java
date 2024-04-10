package io.github.zhoujunlin94.example.web.springmvc.mvc01;

import cn.hutool.core.collection.CollUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.lang.reflect.Method;

/**
 * @author zhoujunlin
 * @date 2024/3/21 22:38
 * @desc
 */
public class TestMVC01 {


    @SneakyThrows
    public static void main(String[] args) {
        // 1. 准备容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebConfig.class);

        // 2. 准备RequestMappingHandlerAdapter  测试：获取模型工厂并获取初始化时配置在ControllerAdvice中的模型数据
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setApplicationContext(applicationContext);
        requestMappingHandlerAdapter.afterPropertiesSet();
        Method getModelFactoryMethod = RequestMappingHandlerAdapter.class.getDeclaredMethod("getModelFactory", HandlerMethod.class, WebDataBinderFactory.class);
        getModelFactoryMethod.setAccessible(true);

        // 3. 准备请求
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setParameter("name", "zhoujunlin");
        ServletWebRequest servletWebRequest = new ServletWebRequest(mockHttpServletRequest);

        // 4. 准备servletInvocableHandlerMethod
        ServletInvocableHandlerMethod servletInvocableHandlerMethod = new ServletInvocableHandlerMethod(new WebConfig.Controller1(), WebConfig.Controller1.class.getDeclaredMethod("foo", WebConfig.User.class));
        // 这里没有特殊参数绑定  入参传null
        ServletRequestDataBinderFactory servletRequestDataBinderFactory = new ServletRequestDataBinderFactory(null, null);
        servletInvocableHandlerMethod.setDataBinderFactory(servletRequestDataBinderFactory);
        servletInvocableHandlerMethod.setParameterNameDiscoverer(new DefaultParameterNameDiscoverer());
        servletInvocableHandlerMethod.setHandlerMethodArgumentResolvers(argumentResolverComposite(applicationContext));
        // 暂不研究返回值处理器

        // 5. 准备ModelAndViewContainer容器
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();

        // 6. 获取模型工厂并初始化模型数据
        ModelFactory modelFactory = (ModelFactory) getModelFactoryMethod.invoke(requestMappingHandlerAdapter, servletInvocableHandlerMethod, servletRequestDataBinderFactory);
        modelFactory.initModel(servletWebRequest, modelAndViewContainer, servletInvocableHandlerMethod);

        // 7. 方法执行
        servletInvocableHandlerMethod.invokeAndHandle(
                servletWebRequest,
                modelAndViewContainer);

        System.out.println("modelAndViewContainer.getModel() = " + modelAndViewContainer.getModel());

        // 8. 关闭容器
        applicationContext.close();

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
                // @RequestBody
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
