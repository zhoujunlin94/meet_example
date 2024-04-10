package io.github.zhoujunlin94.example.web.spring.circularreference;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author zhoujunlin
 * @date 2024年03月25日 11:34
 * @desc
 */
@Setter
@Service
@RequiredArgsConstructor
public class AService {

    /*@Autowired
    private BService bService;

    // @Async也是通过AOP实现的  再最后一步进行增强  不在初始化前增强  循环依赖解决不了
    @Async
    public void foo() {
        System.out.println("AService.foo");
    }*/


    private final BService bService;

}
