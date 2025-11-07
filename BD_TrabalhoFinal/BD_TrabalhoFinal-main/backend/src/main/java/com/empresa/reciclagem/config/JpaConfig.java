package com.empresa.reciclagem.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.empresa.reciclagem.repository.mysql")
@EntityScan(basePackages = "com.empresa.reciclagem.model.mysql")
public class JpaConfig {
}
