package io.github.zhoujunlin94.example.web.spring.scope;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

/**
 * @author zhoujunlin
 * @date 2024年02月29日 19:18
 * @desc scope作用域
 * <p>
 * 获取多例bean的方式
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class, MapperAutoConfiguration.class})
public class TestScope02 {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestScope02.class, args);
        E e = applicationContext.getBean(E.class);
        System.out.println("==========F1========");
        for (int i = 0; i < 3; i++) {
            // F1对象都是同一个   多例无效果
            System.out.println(e.getF1());
        }
        System.out.println("==========F1========");


        System.out.println("==========F2========");
        for (int i = 0; i < 3; i++) {
            // @Lazy   F2对象都不相同  多例生效
            System.out.println(e.getF2());
        }
        System.out.println("==========F2========");

        System.out.println("==========F3========");
        for (int i = 0; i < 3; i++) {
            // proxyMode = ScopedProxyMode.TARGET_CLASS F3对象都不相同  多例生效
            System.out.println(e.getF3());
        }
        System.out.println("==========F3========");


        System.out.println("==========F4========");
        for (int i = 0; i < 3; i++) {
            // ObjectFactory 对象都不相同  多例生效
            System.out.println(e.getF4());
        }
        System.out.println("==========F4========");

        System.out.println("==========F12========");
        for (int i = 0; i < 3; i++) {
            // ApplicationContext 对象都不相同  多例生效
            System.out.println(e.getF12());
        }
        System.out.println("==========F12========");

        applicationContext.close();

    }

}
