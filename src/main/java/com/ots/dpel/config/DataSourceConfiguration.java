package com.ots.dpel.config;

import com.zaxxer.hikari.HikariDataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DataSourceConfiguration {
    
    @Autowired
    private Environment env;
    
    
    @Bean(name = "dataSource")
    //@Bean
    public HikariDataSource dataSourceSpied() {

//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//        config.setJdbcUrl(env.getProperty("jdbc.url"));
//        config.setUsername(env.getProperty("jdbc.username"));
//        config.setPassword(env.getProperty("jdbc.password"));
//
//        config.setMaximumPoolSize(10);
//        config.setAutoCommit(false);
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        HikariDataSource dataSource = new HikariDataSource();
        
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setConnectionTimeout(360000);
        
        return dataSource;
    }
    
    
    @Bean(name = "externalDataSource")
    public HikariDataSource externalDataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        
        dataSource.setDriverClassName(env.getProperty("ext.jdbc.driverClassName"));
        dataSource.setJdbcUrl(env.getProperty("ext.jdbc.url"));
        dataSource.setUsername(env.getProperty("ext.jdbc.username"));
        dataSource.setPassword(env.getProperty("ext.jdbc.password"));
        dataSource.setConnectionTimeout(360000);
        
        return dataSource;
    }
    
    @Bean
    //@Bean(name="dataSource")
    public Log4jdbcProxyDataSource dataSource() {
        
        Log4jdbcProxyDataSource log4jdbcProxyDataSource = new Log4jdbcProxyDataSource(dataSourceSpied());
        
        Log4JdbcCustomFormatter log4JdbcCustomFormatter = new Log4JdbcCustomFormatter();
        
        log4JdbcCustomFormatter.setLoggingType(LoggingType.SINGLE_LINE);
        log4JdbcCustomFormatter.setSqlPrefix("SQL:::");
        
        log4jdbcProxyDataSource.setLogFormatter(log4JdbcCustomFormatter);
        
        return log4jdbcProxyDataSource;
        
    }
    
    @Bean(name = "batchDataSource")
    public HikariDataSource batchDataSource() {
        
        HikariDataSource dataSource = new HikariDataSource();
        
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        
        dataSource.setAutoCommit(false);
        
        return dataSource;
    }
}
