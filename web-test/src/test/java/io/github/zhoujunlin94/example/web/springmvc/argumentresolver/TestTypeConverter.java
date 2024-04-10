package io.github.zhoujunlin94.example.web.springmvc.argumentresolver;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.SimpleTypeConverter;

import java.util.Date;

/**
 * @author zhoujunlin
 * @date 2024/3/19 21:50
 * @desc 测试类型转换
 */
public class TestTypeConverter {

    public static void main(String[] args) {
        //testSimpleTypeConverter();
        //testBeanWrapper();
        testDirectFieldAccessor();
    }


    private static void testSimpleTypeConverter() {
        // 仅有类型转换功能
        SimpleTypeConverter simpleTypeConverter = new SimpleTypeConverter();
        Integer num = simpleTypeConverter.convertIfNecessary("12", int.class);
        Date date = simpleTypeConverter.convertIfNecessary("2023/03/19", Date.class);
        System.out.println("num = " + num);
        System.out.println("date = " + date);
    }

    private static void testBeanWrapper() {
        Bean1 target = new Bean1();
        // 通过反射调用Bean的get set方法  所操作的属性必须要有set方法
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(target);
        beanWrapper.setPropertyValue("a", "123");
        beanWrapper.setPropertyValue("b", "456");
        beanWrapper.setPropertyValue("c", "2023/03/19");
        System.out.println("target = " + target);
    }

    private static void testDirectFieldAccessor() {
        Bean2 target = new Bean2();
        // 通过反射调用  直接操作bean的属性
        DirectFieldAccessor directFieldAccessor = new DirectFieldAccessor(target);
        directFieldAccessor.setPropertyValue("a", "123");
        directFieldAccessor.setPropertyValue("b", "456");
        directFieldAccessor.setPropertyValue("c", "2023/03/19");
        System.out.println("target = " + target);
    }

}

@Getter
@Setter
@ToString
class Bean1 {
    private int a;
    private String b;
    private Date c;
}

@ToString
class Bean2 {
    private int a;
    private String b;
    private Date c;
}