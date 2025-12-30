package com.bookcase.demo.controller;

import com.bookcase.demo.dto.LibraryDTO;
import com.bookcase.demo.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library")
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/author/{surname}")
    public LibraryDTO getAllWrittenBooksByAuthor(@PathVariable String surname) {
        return libraryService.getAllWrittenBooksByAuthor(surname);
    }

}
