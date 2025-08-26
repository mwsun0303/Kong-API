package com.sun.kong.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.sun.kong", sqlSessionTemplateRef = "sqlSessionTemplateDb1")
public class Kong_Mybatis_Db1_Config {

    // SqlSessionFactory
    @Bean(name = "sqlSessionFactoryDb1")
    public SqlSessionFactory sqlSessionFactoryDb1(@Qualifier("kong-db-1") DataSource ds1) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds1);

        // mapper XML 파일 위치 설정
        factoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml")
        );

        return factoryBean.getObject();
    }

    // SqlSessionTemplate
    @Bean(name = "sqlSessionTemplateDb1")
    public SqlSessionTemplate sqlSessionTemplateDb1(
        @Qualifier("sqlSessionFactoryDb1") SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}