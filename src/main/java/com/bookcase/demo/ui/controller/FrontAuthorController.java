package com.bookcase.demo.ui.controller;

import com.bookcase.demo.entity.Author;
import com.bookcase.demo.mapper.AuthorMapperMapStructImpl;
import com.bookcase.demo.mapper.BookMapperImpl;
import com.bookcase.demo.service.AuthorService;
import com.bookcase.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("authors",authorMapper.mapAuthorToDTOList(authorService.getAllAuthorsService()));
        return "authors";
    }

    @GetMapping("/books/{surname}")
    public String showBooks(Model model, @PathVariable(name="surname") String surname){
        model.addAttribute("books", authorService.getBooksByAuthorSurname(surname));
        return "booksFromAuthors";
    }

    @GetMapping("/add-author")
    public String showAddNewAuthor(Model model){
        Author author = new Author();
        model.addAttribute("author", author);
        return "newAuthor";
    }

    @PostMapping("/add-author")
    public String showSaveAuthor(@ModelAttribute("author") Author author) {
        authorService.saveAuthorService(author);
        return "savedSuccess";
    }

}
