package io.github.zhoujunlin94.example.web.springmvc.messageconverter;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.nio.charset.StandardCharsets;

/**
 * @author zhoujunlin
 * @date 2024/3/24 20:04
 * @desc
 */
public class TestMessageConverter {


    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    @SneakyThrows
    private static void test1() {
        MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        if (converter.canWrite(User.class, MediaType.APPLICATION_JSON)) {
            converter.write(new User("张三", 30), MediaType.APPLICATION_JSON, outputMessage);
            System.out.println("outputMessage.getBodyAsString() = " + outputMessage.getBodyAsString());
        }
    }

    @SneakyThrows
    private static void test2() {
        MockHttpOutputMessage outputMessage = new MockHttpOutputMessage();
        MappingJackson2XmlHttpMessageConverter converter = new MappingJackson2XmlHttpMessageConverter();

        if (converter.canWrite(User.class, MediaType.APPLICATION_XML)) {
            converter.write(new User("张三", 30), MediaType.APPLICATION_XML, outputMessage);
            System.out.println("outputMessage.getBodyAsString() = " + outputMessage.getBodyAsString());
        }
    }

    @SneakyThrows
    public static void test3() {
        MockHttpInputMessage inputMessage = new MockHttpInputMessage("{\"name\":\"张三\", \"age\": 30}".getBytes(StandardCharsets.UTF_8));

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        if (converter.canRead(User.class, MediaType.APPLICATION_JSON)) {
            User readUser = (User) converter.read(User.class, inputMessage);
            System.out.println(readUser);
        }

    }

    @SneakyThrows
    public static void test4() {
        // 模拟请求
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);

        // 配置请求头 以及 响应类型
        request.addHeader("Accept", "application/xml");
        response.setContentType("application/json");

        // 设置ResponseBody处理的类型转换器
        RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor = new RequestResponseBodyMethodProcessor(
                CollUtil.newArrayList(
                        new MappingJackson2HttpMessageConverter(),
                        new MappingJackson2XmlHttpMessageConverter()
                )
        );

        requestResponseBodyMethodProcessor.handleReturnValue(
                new User("张三", 30),
                new MethodParameter(TestMessageConverter.class.getMethod("user"), -1),
                new ModelAndViewContainer(),
                servletWebRequest
        );
        /**
         * @ResponseBody 是由返回值类型处理器完成解析的   具体转换工作有消息转换器完成  MappingJackson2HttpMessageConverter
         *
         * 具体转换为json还是xml?
         * 首先看响应头里有没有设置response.setContentType 或者 @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
         * 其次看request的请求头里Accept参数
         * 最后看RequestResponseBodyMethodProcessor中消息转换器的顺序
         */
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }

    @ResponseBody
    // @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User user() {
        return null;
    }

    @Data
    static class User {
        private String name;
        private int age;

        @JsonCreator   // 默认jackson会使用无参构造器反序列化  这里强制使用当前带参构造器
        public User(@JsonProperty("name") String name, @JsonProperty("age") int age) {
            this.name = name;
            this.age = age;
        }
    }

}
