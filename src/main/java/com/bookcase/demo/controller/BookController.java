package com.bookcase.demo.controller;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.dto.BookCategory;
import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping()
    public List<BookDTO> getAllBooks(){
        return bookService.getAllBooks()
                .stream()
                .map(bookMapper::mapBookToDto).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<BookDTO> getBooks(@RequestParam String character) {
        return bookService.getAllBooksStartsByCharacter(character)
                .stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable(name = "id") Long id) {
        return bookMapper.mapBookToDto(bookService.getById(id));
    }

    @GetMapping("/author")
    public List<AuthorDTO> getBookAuthor(@RequestParam String title) {
        return this.bookService.getBookAuthor(title);
    }

    @GetMapping("/find-by-category/{category}")
    public List<BookDTO> getBookByCategory(@PathVariable(name = "category") BookCategory category) {
        return bookService.getByCategory(category).stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public void addNewBook(@RequestBody BookDTO bookDTO) {
        bookService.createNewBook(bookMapper.mapBookFromDto(bookDTO), bookDTO.getAuthorId());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBookFromBookcase(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
    }

    @PutMapping("/change/{id}")
    public BookDTO updateBook(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
        return bookService.updateBookDTO(id, bookDTO);
    }

}
