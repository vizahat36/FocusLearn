package com.focuslearn.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Provides an embedded H2 DataSource when not running with the 'postgres' profile.
 */
@Configuration
@Profile("!postgres")
public class DevDataSourceConfig {

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        // Embedded H2 in-memory DB for development convenience
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:focuslearn;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .username("sa")
                .password("")
                .build();
    }
}
