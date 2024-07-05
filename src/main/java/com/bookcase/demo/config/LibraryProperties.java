package com.bookcase.demo.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "library")
@Configuration
@Slf4j
public class LibraryProperties {
    private String baseUrl;
    private String path;
    private String fields;

    @PostConstruct
    public void printProperties(){
        log.info(this.toString());
    }
}
