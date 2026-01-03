package com.bookcase.demo.service;

import com.bookcase.demo.config.LibraryProperties;
import com.bookcase.demo.dto.LibraryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryService {
    private final RestClient restClient;
    private final LibraryProperties libraryProperties;

    public LibraryDTO getAllWrittenBooksByAuthor(String author) {
        String url = UriComponentsBuilder.fromUriString(libraryProperties.getPath())
                .queryParam("author", author)
                .build()
                .toUriString();

        LibraryDTO libraryDTO = restClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(LibraryDTO.class)
                .getBody();

        return libraryDTO;
    }
}
