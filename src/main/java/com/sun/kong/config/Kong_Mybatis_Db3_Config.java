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
@MapperScan(basePackages = "com.sun.kong", sqlSessionTemplateRef = "sqlSessionTemplateDb3")
public class Kong_Mybatis_Db3_Config {
	
    @Bean(name = "sqlSessionFactoryDb3")
    public SqlSessionFactory sqlSessionFactoryDb3(@Qualifier("kong-db-3") DataSource ds3) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds3);
        
        // mapper XML 파일 위치 설정
        factoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml")
        );
        
        return factoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplateDb3")
    public SqlSessionTemplate sqlSessionTemplateDb1(
        @Qualifier("sqlSessionFactoryDb3") SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}