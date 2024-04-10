package io.github.zhoujunlin94.example.web.springmvc.argumentresolver;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPart;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhoujunlin
 * @date 2024/3/17 21:53
 * @desc 测试参数解析器
 * org.springframework.web.method.annotation.RequestParamMethodArgumentResolver@470a9030
 * org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver@66d57c1b
 * org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver@27494e46
 * org.springframework.web.servlet.mvc.method.annotation.PathVariableMapMethodArgumentResolver@d59970a
 * org.springframework.web.servlet.mvc.method.annotation.MatrixVariableMethodArgumentResolver@1e411d81
 * org.springframework.web.servlet.mvc.method.annotation.MatrixVariableMapMethodArgumentResolver@53b98ff6
 * org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor@3e6fd0b9
 * org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor@7fcff1b9
 * org.springframework.web.servlet.mvc.method.annotation.RequestPartMethodArgumentResolver@697446d4
 * org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver@76adb233
 * org.springframework.web.method.annotation.RequestHeaderMapMethodArgumentResolver@36074e47
 * org.springframework.web.servlet.mvc.method.annotation.ServletCookieValueMethodArgumentResolver@36453307
 * org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver@7dcc91fd
 * org.springframework.web.servlet.mvc.method.annotation.SessionAttributeMethodArgumentResolver@66eb985d
 * org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver@6a9287b1
 * org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver@75504cef
 * org.springframework.web.servlet.mvc.method.annotation.ServletResponseMethodArgumentResolver@6c8a68c1
 * org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor@56193c7d
 * org.springframework.web.servlet.mvc.method.annotation.RedirectAttributesMethodArgumentResolver@28c88600
 * org.springframework.web.method.annotation.ModelMethodProcessor@5f8890c2
 * org.springframework.web.method.annotation.MapMethodProcessor@607b2792
 * org.springframework.web.method.annotation.ErrorsMethodArgumentResolver@7f9e1534
 * org.springframework.web.method.annotation.SessionStatusMethodArgumentResolver@138a7441
 * org.springframework.web.servlet.mvc.method.annotation.UriComponentsBuilderMethodArgumentResolver@81ff872
 * io.github.zhoujunlin94.example.web.springmvc.dispatchservlet.TokenArgumentResolver@31611954
 * org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver@3e598df9
 * org.springframework.web.method.annotation.RequestParamMethodArgumentResolver@7e31ce0f
 * org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor@99a65d3
 */
public class TestArgumentResolver {

    @SneakyThrows
    public static void main(String[] args) {
        // 0. 准备Spring环境
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebConfig.class);
        DefaultListableBeanFactory beanFactory = applicationContext.getDefaultListableBeanFactory();

        // 1. 准备请求
        HttpServletRequest request = mockRequest();
        // 2. 准备控制器方法
        HandlerMethod handlerMethod = new HandlerMethod(new TestController(), TestController.class.getMethod("test", String.class, String.class, int.class, String.class, MultipartFile.class, int.class, String.class, String.class, String.class, HttpServletRequest.class, User.class, User.class, User.class));

        // 3. 准备绑定对象的类型转换  处理数据类型转换  例如 字符串->数字
        ServletRequestDataBinderFactory servletRequestDataBinderFactory = new ServletRequestDataBinderFactory(null, null);

        // 4. 准备 ModelAndViewContainer容器存储中间Model对象
        ModelAndViewContainer modelAndViewContainer = new ModelAndViewContainer();

        // 组合模式
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

        // 5. 解析每个参数的值
        for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
            // 获取这个参数的注解
            String annotationVal = Arrays.stream(methodParameter.getParameterAnnotations()).map(annotation -> annotation.annotationType().getSimpleName()).collect(Collectors.joining());
            String paramAnnotation = StrUtil.isNotBlank(annotationVal) ? "@" + annotationVal + " " : " ";
            // 参数名生成
            methodParameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());
            StringBuilder ret = new StringBuilder("[").append(methodParameter.getParameterIndex()).append("] ").append(paramAnnotation)
                    .append(methodParameter.getParameterType().getSimpleName()).append(" ").append(methodParameter.getParameterName());
            if (resolverComposite.supportsParameter(methodParameter)) {
                Object paramValue = resolverComposite.resolveArgument(methodParameter, modelAndViewContainer, new ServletWebRequest(request), servletRequestDataBinderFactory);
                // System.out.println("解析的参数类型" + paramValue.getClass());
                ret.append("->").append(paramValue);
            }
            System.out.println(ret);
            System.out.println("模型数据为：" + modelAndViewContainer.getModel());
        }


    }

    private static HttpServletRequest mockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        // @RequestParam
        request.setParameter("name1", "张三");
        request.setParameter("name2", "李四");
        request.setParameter("age", "18");
        // 文件
        request.addPart(new MockPart("file", "abc", "hello world".getBytes(StandardCharsets.UTF_8)));
        // @PathVariable  解析路径参数并放入RequestAttribute中
        Map<String, String> uriTemplateVariables = new AntPathMatcher().extractUriTemplateVariables("/test/{id}", "/test/124");
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriTemplateVariables);
        // @RequestHeader
        request.setContentType("application/json");
        // @CookieValue
        request.setCookies(new Cookie("token", "2345"));
        // @ModelAttribute
        request.setParameter("name", "张三");
        // @RequestBody
        request.setContent("{\"name\": \"李四\", \"age\":\"20\"}".getBytes(StandardCharsets.UTF_8));

        return new StandardServletMultipartResolver().resolveMultipart(request);
    }


}

class TestController {
    public void test(
            @RequestParam("name1") String name1,   // name1=张三
            String name2,                           // name2=李四       不带注解@RequestParam
            @RequestParam("age") int age,           // age=18    默认字符串  需要处理类型转换
            @RequestParam(name = "home", defaultValue = "${JAVA_HOME}") String home1,   // Spring环境获取数据
            @RequestParam("file") MultipartFile file,    //文件上传
            @PathVariable("id") int id,                // 请求路径参数    /test/124    test/{id}
            @RequestHeader("Content-Type") String header,   // 请求头参数
            @CookieValue("token") String token,      // cookie获取数据
            @Value("${JAVA_HOME}") String home2,     // Spring环境获取数据
            HttpServletRequest request,           // request,response,session
            @ModelAttribute("abc") User user1,   // name=张三&age=18
            User user2,                           // name=张三&age=18  省略@ModelAttribute
            @RequestBody User user3         //   json
    ) {
    }
}


@Configuration
class WebConfig {
}

@Data
class User {
    private String name;
    private int age;
}
