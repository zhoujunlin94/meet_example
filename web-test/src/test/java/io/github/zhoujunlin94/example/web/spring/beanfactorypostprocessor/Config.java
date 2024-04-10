package io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor;

import com.zaxxer.hikari.HikariDataSource;
import io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor.component.Bean1;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhoujunlin
 * @date 2024/2/25 20:18
 * @desc
 */
@Configuration
@ComponentScan(basePackages = "io.github.zhoujunlin94.example.web.spring.beanfactorypostprocessor.component")
public class Config {

    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean
    public DataSource dataSource() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUsername("root");
        dataSourceProperties.setPassword("root");
        dataSourceProperties.setUrl("jdbc:mysql://127.0.0.1:3306/base?useSSL=false&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull&useUnicode=true&autoReconnect=true&failOverReadOnly=false");
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    /*@Bean
    public MapperFactoryBean<Mapper1> mapper1(SqlSessionFactory sqlSessionFactory){
        MapperFactoryBean<Mapper1> mapper1MapperFactoryBean = new MapperFactoryBean<>(Mapper1.class);
        mapper1MapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return mapper1MapperFactoryBean;
    }

    @Bean
    public MapperFactoryBean<Mapper2> mapper2(SqlSessionFactory sqlSessionFactory){
        MapperFactoryBean<Mapper2> mapper2MapperFactoryBean = new MapperFactoryBean<>(Mapper2.class);
        mapper2MapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return mapper2MapperFactoryBean;
    }*/

}
