package io.github.zhoujunlin94.example.web.springmvc.argumentresolver;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zhoujunlin
 * @date 2024/3/19 22:45
 * @desc 测试获取范型名字
 */
public class TestGenericTypeName {

    static class Teacher {

    }

    static class TeacherMapper {

    }

    static class BaseDAO<M, E> {

    }

    static class TeacherDAO extends BaseDAO<TeacherMapper, Teacher> {

    }

    public static void main(String[] args) {
        //testJDKGetGenericTypeName();
        testSpringGetGenericTypeName();
    }

    private static void testJDKGetGenericTypeName() {
        // 获取带有范型的父类
        Type genericSuperclass = TeacherDAO.class.getGenericSuperclass();
        System.out.println(genericSuperclass);

        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            System.out.println(parameterizedType.getActualTypeArguments()[0]);
            System.out.println(parameterizedType.getActualTypeArguments()[1]);
        }
    }

    private static void testSpringGetGenericTypeName() {
        Class<?>[] typeArguments = GenericTypeResolver.resolveTypeArguments(TeacherDAO.class, BaseDAO.class);
        for (Class<?> typeArgument : typeArguments) {
            System.out.println(typeArgument);
        }
    }


}
