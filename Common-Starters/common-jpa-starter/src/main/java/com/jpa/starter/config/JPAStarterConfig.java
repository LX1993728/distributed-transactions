package com.jpa.starter.config;

import com.jpa.starter.domain.RepositoryProperties;
import com.jpa.starter.domain.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.jpa.starter"})
@PropertySource(value = {"classpath:jpa-starter.properties"},ignoreResourceNotFound = true)
@EnableConfigurationProperties({SwaggerProperties.class, RepositoryProperties.class})
@EnableJpaRepositories(basePackages = {"${repository.scan.base}"})
public class JPAStarterConfig {
}
