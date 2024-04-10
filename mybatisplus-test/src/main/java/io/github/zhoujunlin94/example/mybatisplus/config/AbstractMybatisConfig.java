package io.github.zhoujunlin94.example.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.MySqlDialect;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zhoujunlin
 * @date 2023/03/27
 **/
public abstract class AbstractMybatisConfig {

    protected abstract DataSourceProperties dataSourceProperties();

    protected abstract HikariDataSource dataSource(DataSourceProperties dataSourceProperties);

    protected abstract PlatformTransactionManager platformTransactionManager(HikariDataSource dataSource);

    protected abstract MybatisPlusInterceptor mybatisPlusInterceptor();

    protected PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setDialect(new MySqlDialect());
        paginationInnerInterceptor.setOverflow(true);
        paginationInnerInterceptor.setOptimizeJoin(true);
        return paginationInnerInterceptor;
    }

    protected abstract SqlSessionFactory sqlSessionFactory(HikariDataSource dataSource) throws Exception;

    protected SqlSessionFactory buildSqlSessionFactory(HikariDataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setDefaultEnumTypeHandler(EnumOrdinalTypeHandler.class);
        sqlSessionFactoryBean.setConfiguration(mybatisConfiguration);

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        //引入自定以自动填充类
        globalConfig.setMetaObjectHandler(getMetaObjectHandler());
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setPropertyFormat("`%s`");
        dbConfig.setColumnFormat("`%s`");
        globalConfig.setDbConfig(dbConfig);
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);

        sqlSessionFactoryBean.setTypeAliasesPackage(getTypeAliasesPackage());
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(getMapperLocation())
        );
        // 插件
        sqlSessionFactoryBean.setPlugins(mybatisPlusInterceptor());

        return sqlSessionFactoryBean.getObject();
    }

    protected abstract MetaObjectHandler getMetaObjectHandler();

    protected abstract String getTypeAliasesPackage();

    protected abstract String getMapperLocation();

}
