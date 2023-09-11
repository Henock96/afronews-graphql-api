package com.dreesis.afronews_graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.platform.config.Config;
import sh.platform.config.MySQL;

import javax.sql.DataSource;
/*
@Configuration
public class DataSourceConfig {
    @Bean(name = "dataSource")
    public DataSource getDataSource(){
        Config config = new Config();
        MySQL db = config.getCredential("db", MySQL::new);
        return db.get();
    }
}
*/