package com.bookcase.demo.service;


import com.bookcase.demo.config.LibraryClientConfig;
import com.bookcase.demo.dto.LibraryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

//@RestClientTest(RestClient.class)
public class LibraryServiceTest {

//    @Autowired
//    MockRestServiceServer mockRestServiceServer;
//
//    @Autowired
//    private RestClient libraryClient;
//
//    @Autowired
//    private LibraryService libraryService;
//
//    @Autowired
//    ObjectMapper objectMapper;

//    @Test
//    public void getAllWrittenBooksByAuthor() throws JsonProcessingException {
//        //given
//        String author = "dupa";
//        mockRestServiceServer.expect(requestTo("https://openlibrary.org/search.json?q=dupa"))
//                .andRespond(withSuccess(objectMapper.writeValueAsString(new LibraryDTO()), MediaType.APPLICATION_JSON));
//
//        //when
//        LibraryDTO libraryDTO = libraryService.getAllWrittenBooksByAuthor(author);
//
//        //then
//        assertThat(libraryDTO).isNotNull();
//    }
}
