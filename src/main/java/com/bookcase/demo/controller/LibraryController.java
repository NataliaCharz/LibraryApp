package com.bookcase.demo.controller;

import com.bookcase.demo.dto.LibraryDTO;
import com.bookcase.demo.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping()
    public LibraryDTO getAllWrittenBooksByAuthor(String author){
        return libraryService.getAllWrittenBooksByAuthor(author);
    }

}
