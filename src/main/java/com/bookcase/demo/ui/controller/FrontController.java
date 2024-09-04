package com.bookcase.demo.ui.controller;

import com.bookcase.demo.controller.AuthorController;
import com.bookcase.demo.mapper.AuthorMapperMapStructImpl;
import com.bookcase.demo.mapper.BookMapperImpl;
import com.bookcase.demo.service.AuthorService;
import com.bookcase.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
//@ApiIgnore
public class FrontController {
    private final AuthorMapperMapStructImpl authorMapper;
    private final AuthorService authorService;
    private final BookMapperImpl bookMapper;
    private final BookService bookService;

    @GetMapping()
    public String home(Model model){
        model.addAttribute("features", Arrays.asList(
                "Create Your own Bookcase and keep track with books You're about to read and You've already read",
                "Keep information about Authors and Their' books in one place",
                "Find more books to read written by Your favourite Author"
        ));
        return "home";
    }

//    @GetMapping("/new-form")
//    public String showNewBookForm(Model model){
//        return "newForm" +
//                "";
//    }

    @GetMapping("/authors")
    public String getAuthors(Model model) {
        model.addAttribute("authors",authorMapper.mapAuthorToDtoList(authorService.getAllAuthors()));
        return "authors";
    }

    @GetMapping("/books")
    public String getBooks(Model model){
        model.addAttribute("books",bookMapper.mapBookToDtoList(bookService.getAllBooks()));
        return "books";
    }

}
