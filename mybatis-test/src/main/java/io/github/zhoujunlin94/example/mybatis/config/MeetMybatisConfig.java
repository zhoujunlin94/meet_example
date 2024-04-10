package io.github.zhoujunlin94.example.mybatis.config;

import com.zaxxer.hikari.HikariDataSource;
import io.github.zhoujunlin94.meet.tk_mybatis.config.AbstractMybatisConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zhoujunlin
 * @date 2023/03/27
 **/
@Configuration
@MapperScan(basePackages = "io.github.zhoujunlin94.example.mybatis.mapper.meet", annotationClass = Mapper.class,
        sqlSessionFactoryRef = "meetSqlSessionFactory")
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class MeetMybatisConfig extends AbstractMybatisConfig {

    public static final String DATA_SOURCE_PROPERTIES = "meetDataSourceProperties";
    public static final String DATA_SOURCE = "meetDataSource";
    public static final String SQL_SESSION_FACTORY = "meetSqlSessionFactory";
    public static final String TRANSACTION_MANAGER = "meetTransactionManager";

    private static final String MAPPER_LOCATION = "classpath:mybatis/meet/*.xml";
    private static final String TYPE_ALIASES_PACKAGE = "io.github.zhoujunlin94.example.mybatis.model.meet";

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
    @Primary
    @Bean(SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory(@Autowired @Qualifier(DATA_SOURCE) HikariDataSource dataSource) throws Exception {
        return buildSqlSessionFactory(dataSource);
    }

    @Override
    protected boolean enablePrintSQLInterceptor() {
        return true;
    }

    @Override
    @Primary
    @Bean(TRANSACTION_MANAGER)
    public PlatformTransactionManager platformTransactionManager(@Autowired @Qualifier(DATA_SOURCE) HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Override
    protected String getTypeAliasesPackage() {
        return TYPE_ALIASES_PACKAGE;
    }

    @Override
    protected String getMapperLocation() {
        return MAPPER_LOCATION;
    }

}
