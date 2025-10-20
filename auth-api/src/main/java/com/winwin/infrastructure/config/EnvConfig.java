package com.winwin.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
@PropertySource(value = "file:${env.file.path:.env}", ignoreResourceNotFound = true)
public class EnvConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

        // Поднимаемся на уровень выше от модуля к корню проекта
        File envFile = new File("../.env");
        if (!envFile.exists()) {
            // Если запускается из корня проекта
            envFile = new File(".env");
        }

        configurer.setLocation(new FileSystemResource(envFile));
        configurer.setIgnoreResourceNotFound(true);
        return configurer;
    }
}