package com.sulwep7.tarotpointcounterback.configuration.dataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.sulwep7.tarotpointcounterback.mapper")
public class DataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "jdbc.mysql")
    public DataSource mySqlDataSource() {
        PooledDataSource dataSource = new PooledDataSource();

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(mySqlDataSource());
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:script/mySql/*.xml"));

        return factoryBean.getObject();
    }
}
