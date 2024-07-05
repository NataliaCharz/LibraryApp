package com.bookcase.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Configuration
@RequiredArgsConstructor
@Component
public class LibraryClientConfig {
    private final LibraryProperties libraryProperties;
//    private final RestClient restClient;

//    @Autowired
//    public LibraryClientConfig(RestClient restClient, LibraryProperties libraryProperties){
//        this.libraryProperties = libraryProperties;
//        this.restClient = RestClient.builder()
//                .baseUrl(libraryProperties.getBaseUrl())
//                .build();
//    }
    @Bean
    public RestClient libraryClient(){
        return RestClient.builder()
                .baseUrl(libraryProperties.getBaseUrl())
                .build();
    }
}
