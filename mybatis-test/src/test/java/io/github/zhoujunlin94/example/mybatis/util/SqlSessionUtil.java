package io.github.zhoujunlin94.example.mybatis.util;

import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author zhoujunlin
 * @date 2024年04月07日 15:42
 * @desc
 */
public final class SqlSessionUtil {

    @SneakyThrows
    public static SqlSession getSqlSession(String resource, boolean autoCommit) {
        // 加载核心配置文件
        InputStream configStream = Resources.getResourceAsStream(resource);
        // 获取SqlSessionFactory
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(configStream);
        // 获取SqlSession  自动提交
        return sqlSessionFactory.openSession(autoCommit);
    }

    public static SqlSession getSqlSession() {
        return getSqlSession("mybatis-config.xml", true);
    }

    public static <T> T getMapper(Class<T> mapperClass) {
        return getSqlSession().getMapper(mapperClass);
    }

}
