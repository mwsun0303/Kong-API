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
@MapperScan(basePackages = "com.sun.kong", sqlSessionTemplateRef = "sqlSessionTemplateDb2")
public class Kong_Mybatis_Db2_Config {
	
    @Bean(name = "sqlSessionFactoryDb2")
    public SqlSessionFactory sqlSessionFactoryDb2(@Qualifier("kong-db-2") DataSource ds2) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds2);
        
        // mapper XML 파일 위치 설정
        factoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml")
        );
        
        return factoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplateDb2")
    public SqlSessionTemplate sqlSessionTemplateDb1(
        @Qualifier("sqlSessionFactoryDb2") SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}