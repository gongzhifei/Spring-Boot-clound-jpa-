package com.xxd.config;

import com.xxd.util.BaseJdbcTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * @author gongzhifei
 */
@Configuration
public class DataSourceConfig {

    /**
     * manager库数据源配置
     * @return
     */
    @Bean(name = "managerSource")
    @Qualifier("managerSource")
    @Primary
    @ConfigurationProperties(prefix = "custom.datasource.manager")
    public DataSource manager(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 报表库数据源配置
     * @return
     */
    @Bean(name = "reportSource")
    @Qualifier("reportSource")
    @ConfigurationProperties(prefix = "custom.datasource.report")
    public DataSource report(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "managerJdbcTemplate")
    public BaseJdbcTemplate primaryJdbcTemplate(
            @Qualifier("managerSource") DataSource dataSource) {
        return new BaseJdbcTemplate(dataSource);
    }

    @Bean(name = "reportJdbcTemplate")
    public BaseJdbcTemplate secondaryJdbcTemplate(
            @Qualifier("reportSource") DataSource dataSource) {
        return new BaseJdbcTemplate(dataSource);
    }



}
