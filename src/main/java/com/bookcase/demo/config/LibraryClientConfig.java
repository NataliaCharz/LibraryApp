package com.bookcase.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Configuration
@RequiredArgsConstructor
public class LibraryClientConfig {
    private final LibraryProperties libraryProperties;

    @Bean
    public RestClient libraryClient(){
        return RestClient.builder()
                .baseUrl(libraryProperties.getBaseUrl())
                .build();
    }
}
