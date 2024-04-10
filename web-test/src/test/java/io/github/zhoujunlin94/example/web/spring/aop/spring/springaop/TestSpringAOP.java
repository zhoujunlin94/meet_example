package io.github.zhoujunlin94.example.web.spring.aop.spring.springaop;

import lombok.SneakyThrows;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.aop.framework.Advised;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

import java.lang.reflect.Method;

/**
 * @author zhoujunlin
 * @date 2024/3/31 13:28
 * @desc 依赖注入和初始化影响的原始对象
 * 代理与目标是两个对象，两者成员变量并不共用
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class, MapperAutoConfiguration.class})
public class TestSpringAOP {

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestSpringAOP.class, args);
        Bean100 proxy = applicationContext.getBean(Bean100.class);

        showProxyAndTarget(proxy);

        // 调用目标方法   方法会被增强  final  static private除外
        System.out.println("proxy.getBean2() = " + proxy.getBean2());
        System.out.println("proxy.isInitialized() = " + proxy.isInitialized());

        proxy.m1();
        proxy.m2();
        proxy.m3();
        Method m4 = Bean100.class.getDeclaredMethod("m4");
        m4.setAccessible(true);
        m4.invoke(proxy);


        applicationContext.close();

    }

    @SneakyThrows
    private static void showProxyAndTarget(Bean100 proxy) {
        System.out.println("====>代理类" + proxy + "中的成员变量");
        // 全局变量的默认值
        System.out.println("proxy.initialized = " + proxy.initialized);
        System.out.println("proxy.bean2 = " + proxy.bean2);

        if (proxy instanceof Advised) {
            Bean100 bean1 = (Bean100) ((Advised) proxy).getTargetSource().getTarget();
            System.out.println("====>目标类" + bean1 + "中的成员变量");
            System.out.println("bean1.initialized = " + bean1.initialized);
            System.out.println("bean1.bean2 = " + bean1.bean2);
        }

    }


}
