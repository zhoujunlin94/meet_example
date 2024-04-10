package io.github.zhoujunlin94.example.web.springmvc.dispatchservlet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.Yaml;

/**
 * @author zhoujunlin
 * @date 2024/3/14 21:55
 * @desc
 */
@Slf4j
@Controller
public class TestController {


    /**
     * 测试get请求
     *
     * @return
     */
    @GetMapping("/test1")
    public ModelAndView test1() {
        log.debug("test1");
        return null;
    }

    /**
     * 测试带入参注解的post请求
     *
     * @param name
     * @return
     */
    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam String name) {
        log.debug("test2({})", name);
        return null;
    }

    /**
     * 测试带自定义注解的入参处理器   put请求
     *
     * @param token
     * @return
     */
    @PutMapping("/test3")
    public ModelAndView test3(@Token String token) {
        log.debug("test3({})", token);
        return null;
    }

    /**
     * 不定义请求类型  自定义返回类型处理器
     *
     * @return
     */
    @RequestMapping("/test4")
    @Yml
    public User test4() {
        log.debug("test4()");
        return new User("张三", 30);
    }

}

@Data
@AllArgsConstructor
class User {
    private String name;
    private int age;

    public static void main(String[] args) {
        String userYaml = new Yaml().dump(new User("张三", 30));
        System.out.println(userYaml);
    }

}
