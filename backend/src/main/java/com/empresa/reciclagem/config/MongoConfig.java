package com.empresa.reciclagem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.empresa.reciclagem.repository.mongodb")
public class MongoConfig {
}
