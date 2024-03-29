package com.deepakallcode.codesnippetmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Value("${database.url}")
    private String databaseUrl;
    @Value("${database.password}")
    private String databasePassword;
    @Value("${database.username}")
    private String databaseUsername;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(System.getenv("DATABASE_URL") != null ? System.getenv("DATABASE_URL") : databaseUrl);
        dataSource.setUsername(System.getenv("DATABASE_USERNAME") != null ? System.getenv("DATABASE_USERNAME"): databaseUsername);
        dataSource.setPassword(System.getenv("DATABASE_PASSWORD") != null ? System.getenv("DATABASE_PASSWORD"): databasePassword);
        return dataSource;
    }
}
