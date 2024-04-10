package io.github.zhoujunlin94.example.web.spring.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2024年02月29日 19:22
 * @desc
 */
@RestController
public class TestController {
    /**
     * 单例Bean引入非单例Bean需要添加@Lazy注解（还有其它方式）
     * 否则引入的对象仍表现为单例
     */

    @Lazy
    @Autowired
    private Bean4Request bean4Request;

    @Lazy
    @Autowired
    private Bean4Session bean4Session;

    @Lazy
    @Autowired
    private Bean4Application bean4Application;

    @GetMapping(value = "/test")
    public String test() {
        return "<ul>" +
                "<li> request scope:" + bean4Request + "</li>" +
                "<li> session scope:" + bean4Session + "</li>" +
                "<li> application scope:" + bean4Application + "</li>" +
                "</ul>";
    }

}
