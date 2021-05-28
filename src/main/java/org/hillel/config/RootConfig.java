package org.hillel.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:config/database.properties")
//@ComponentScan({"org.hillel.persistence", "org.hillel.service", "org.hillel.dto.converter"})
@ComponentScan({"org.hillel.persistence", "org.hillel.service"})
@EnableJpaRepositories(
        basePackages = {"org.hillel.persistence.jpa.repository"},
        entityManagerFactoryRef = "emf"
)
@EnableTransactionManagement

public class RootConfig {

    @Bean
    public DataSource dataSource(
            @Value("${postgres.username}") String userName,
            @Value("${postgres.password}") String password,
            @Value("${postgres.databaseName}") String databaseName,
            @Value("${postgres.serverName}") String serverName
    ) {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(PGSimpleDataSource.class.getName());
        config.setUsername(userName);
        config.setPassword(password);
        config.addDataSourceProperty("databaseName", databaseName);
        config.addDataSourceProperty("serverName", serverName);
        config.setMaximumPoolSize(150);
        config.setMinimumIdle(30);
        return new HikariDataSource(config);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean emf(
            DataSource dataSource,
            @Value("${hibernate.hbm2ddl}") String hbm2ddl,
            @Value("${hibernate.show_sql}") String show_sql,
            @Value("${hibernate.query.timeout}") int timeout
    ){
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("org.hillel.persistence.entity");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties properties = new Properties();
        properties.put("hibernate.dialect", PostgreSQL10Dialect.class.getName());
        properties.put("hibernate.hbm2ddl.auto",hbm2ddl); //create create-drop update validate
        properties.put("hibernate.show_sql",show_sql);
        properties.put("javax.persistence.query.timeout", timeout);
        emf.setJpaProperties(properties);
        return emf;
    }

    @Bean
    PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory,
            DataSource dataSource
    ){
        JpaTransactionManager TransactionManager = new JpaTransactionManager();
        TransactionManager.setEntityManagerFactory(entityManagerFactory);
        TransactionManager.setDataSource(dataSource);
        return TransactionManager;
    }
}
