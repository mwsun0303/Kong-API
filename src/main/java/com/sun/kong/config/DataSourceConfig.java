package com.sun.kong.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

    // --- DataSources ---
    @Bean(name = "kong-db-1")
    public DataSource psql_01(
            @Value("${spring.datasource.kong-db-1.url}") String url,
            @Value("${spring.datasource.kong-db-1.username}") String username,
            @Value("${spring.datasource.kong-db-1.password}") String password,
            @Value("${spring.datasource.kong-db-1.driverClassName}") String driverClassName) {

        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    @Bean(name = "kong-db-2")
    public DataSource psql_02(
            @Value("${spring.datasource.kong-db-2.url}") String url,
            @Value("${spring.datasource.kong-db-2.username}") String username,
            @Value("${spring.datasource.kong-db-2.password}") String password,
            @Value("${spring.datasource.kong-db-2.driverClassName}") String driverClassName) {

        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    @Bean(name = "kong-db-3")
    public DataSource psql_03(
            @Value("${spring.datasource.kong-db-3.url}") String url,
            @Value("${spring.datasource.kong-db-3.username}") String username,
            @Value("${spring.datasource.kong-db-3.password}") String password,
            @Value("${spring.datasource.kong-db-3.driverClassName}") String driverClassName) {

        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    // --- JdbcTemplates ---
    @Bean(name = "jdbcTemplate-kong-db-1")
    public JdbcTemplate jdbcTemplateDb1(@Qualifier("kong-db-1") DataSource ds1) {
        return new JdbcTemplate(ds1);
    }

    @Bean(name = "jdbcTemplate-kong-db-2")
    public JdbcTemplate jdbcTemplateDb2(@Qualifier("kong-db-2") DataSource ds2) {
        return new JdbcTemplate(ds2);
    }

    @Bean(name = "jdbcTemplate-kong-db-3")
    public JdbcTemplate jdbcTemplateDb3(@Qualifier("kong-db-3") DataSource ds3) {
        return new JdbcTemplate(ds3);
    }
}
