package io.github.zhoujunlin94.example.web.spring.beanpostprocessor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2024/2/25 13:18
 * @desc
 */
@Data
public class Bean1 {

    private Bean2 bean2;
    private Bean3 bean3;
    private String home;

    @Autowired
    public void setBean2(Bean2 bean2) {
        // 为了证明@Autowired注解生效  @Autowired注解到方法上
        System.err.println("@Autowired生效 -> " + bean2);
        this.bean2 = bean2;
    }

    @Resource
    public void setBean3(Bean3 bean3) {
        System.err.println("@Resource生效 -> " + bean3);
        this.bean3 = bean3;
    }

    @Autowired
    public void setHome(@Value("${JAVA_HOME}") String home) {
        System.err.println("@Autowired  @Value生效 -> " + home);
        this.home = home;
    }

    @PostConstruct
    public void init() {
        System.err.println("@PostConstruct生效");
    }

    @PreDestroy
    public void destroy() {
        System.err.println("@PreDestroy生效");
    }
}
