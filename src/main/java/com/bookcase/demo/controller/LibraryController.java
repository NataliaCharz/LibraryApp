package com.bookcase.demo.controller;

import com.bookcase.demo.service.LibraryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library")
public class LibraryController {
    private final LibraryService libraryService;
    private final ObjectMapper objectMapper;

    @GetMapping("/author/{surname}")
    public String getAllWrittenBooksByAuthor(@PathVariable String surname) throws JsonProcessingException {
        return objectMapper.writeValueAsString(libraryService.getAllWrittenBooksByAuthor(surname));
    }

}
