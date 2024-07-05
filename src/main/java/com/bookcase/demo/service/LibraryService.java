package com.bookcase.demo.service;

import com.bookcase.demo.config.LibraryProperties;
import com.bookcase.demo.dto.LibraryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryService {
    private final RestClient libraryClient;
    private final LibraryProperties libraryProperties;


    public LibraryDTO getAllWrittenBooksByAuthor(String author){
        ResponseEntity<LibraryDTO> response = libraryClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(libraryProperties.getPath())
                        .queryParam("q", author)
                        .queryParam("fields",libraryProperties.getFields())
                        .build())
                .header(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .retrieve()
                .toEntity(LibraryDTO.class);
        log.info("Response: {}", response);
        return response.getBody();
    }
}
