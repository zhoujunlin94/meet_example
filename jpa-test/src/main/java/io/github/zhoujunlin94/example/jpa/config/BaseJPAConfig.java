package io.github.zhoujunlin94.example.jpa.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhoujunlin
 * @date 2023/03/27
 **/
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "baseEntityManagerFactory",
        transactionManagerRef = "baseTransactionManager",
        //设置Repository所在位置
        basePackages = {"io.github.zhoujunlin94.example.jpa.model.base"})
public class BaseJPAConfig {

    @Resource
    private JpaProperties jpaProperties;
    @Resource
    private HibernateProperties hibernateProperties;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.base")
    public DataSourceProperties baseDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.base.hikari")
    public HikariDataSource baseDataSource(@Autowired @Qualifier("baseDataSourceProperties") DataSourceProperties baseDataSourceProperties) {
        return baseDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean baseEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                // 设置数据源
                .dataSource(baseDataSource(baseDataSourceProperties()))
                // 设置jpa配置
                .properties(jpaProperties.getProperties())
                // 设置hibernate配置
                .properties(getVendorProperties())
                // 设置实体类所在位置
                .packages("io.github.zhoujunlin94.example.jpa.model.base")
                // 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .persistenceUnit("basePersistenceUnit")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager baseTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(baseEntityManagerFactory(builder).getObject()));
    }

    @Bean
    @Primary
    public EntityManager baseEntityManager(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(baseEntityManagerFactory(builder).getObject()).createEntityManager();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }
}
