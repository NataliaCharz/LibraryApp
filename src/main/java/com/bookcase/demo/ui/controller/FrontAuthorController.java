package com.bookcase.demo.ui.controller;

import com.bookcase.demo.entity.Author;
import com.bookcase.demo.mapper.AuthorMapperMapStructImpl;
import com.bookcase.demo.mapper.BookMapperImpl;
import com.bookcase.demo.service.AuthorService;
import com.bookcase.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/front-authors")
@RequiredArgsConstructor
public class FrontAuthorController {
    private final AuthorMapperMapStructImpl authorMapper;
    private final AuthorService authorService;
    private final BookMapperImpl bookMapper;
    private final BookService bookService;

    @GetMapping("/authors")
    public String showGetAuthors(Model model) {
        model.addAttribute("authors",authorMapper.mapAuthorToDTOList(authorService.getAllAuthors()));
        return "authors";
    }

    @GetMapping("/add-author")
    public String showAddNewAuthor(Model model){
        Author author = new Author();
        model.addAttribute("author", author);
        return "newAuthor";
    }

    @PostMapping("/add-author")
    public String showSaveAuthor(@ModelAttribute("author") Author author) {
        authorService.saveAuthor(author);
        return "savedSuccess";
    }

}
