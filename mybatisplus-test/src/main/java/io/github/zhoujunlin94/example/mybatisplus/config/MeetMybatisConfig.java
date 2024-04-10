package io.github.zhoujunlin94.example.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023/03/27
 **/
@Configuration
@MapperScan(basePackages = MeetMybatisConfig.MAPPER_PACKAGE, annotationClass = Mapper.class,
        sqlSessionFactoryRef = MeetMybatisConfig.SQL_SESSION_FACTORY)
public class MeetMybatisConfig extends AbstractMybatisConfig {

    public static final String MAPPER_PACKAGE = "io.github.zhoujunlin94.example.mybatisplus.mapper.meet";
    public static final String SQL_SESSION_FACTORY = "meetSqlSessionFactory";
    public static final String DATA_SOURCE_PROPERTIES = "meetDataSourceProperties";
    public static final String DATA_SOURCE = "meetDataSource";
    public static final String TRANSACTION_MANAGER = "meetTransactionManager";

    @Resource
    private MeetMetaObjectHandler meetMetaObjectHandler;

    @Override
    @Primary
    @Bean(DATA_SOURCE_PROPERTIES)
    @ConfigurationProperties("spring.datasource.meet")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Override
    @Primary
    @Bean(DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.meet.hikari")
    public HikariDataSource dataSource(@Autowired @Qualifier(DATA_SOURCE_PROPERTIES) DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Override
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 攻击 SQL 阻断解析器,防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Override
    @Primary
    @Bean(SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory(@Autowired @Qualifier(DATA_SOURCE) HikariDataSource dataSource) throws Exception {
        return buildSqlSessionFactory(dataSource);
    }

    @Override
    protected MetaObjectHandler getMetaObjectHandler() {
        return meetMetaObjectHandler;
    }

    @Override
    public String getMapperLocation() {
        return "classpath:mybatisplus/meet/*.xml";
    }

    @Override
    public String getTypeAliasesPackage() {
        return "io.github.zhoujunlin94.example.mybatisplus.model.meet";
    }

    @Override
    @Primary
    @Bean(TRANSACTION_MANAGER)
    public PlatformTransactionManager platformTransactionManager(@Autowired @Qualifier(DATA_SOURCE) HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
