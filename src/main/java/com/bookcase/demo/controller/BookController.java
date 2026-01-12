package com.bookcase.demo.controller;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.dto.BookCategory;
import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.service.BookService;
import lombok.RequiredArgsConstructor;
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
    public List<BookDTO> getBooks(){
        return bookService.getBooksService()
                .stream()
                .map(bookMapper::mapBookToDto).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<BookDTO> getBooksContainingCharacters(@RequestParam String character) {
        return bookService.getBooksContainingCharactersService(character)
                .stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable(name = "id") Long id) {
        return bookMapper.mapBookToDto(bookService.getBookByIdService(id));
    }

    @GetMapping("/author")
    public List<AuthorDTO> getBookAuthor(@RequestParam String title) {
        return this.bookService.getBookAuthorService(title);
    }

    @GetMapping("/find-by-category/{category}")
    public List<BookDTO> getBookByCategory(@PathVariable(name = "category") BookCategory category) {
        return bookService.getBookByCategoryService(category).stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public void addNewBook(@RequestBody BookDTO bookDTO) {
        bookService.createNewBookService(bookMapper.mapBookFromDto(bookDTO), bookDTO.getAuthorId());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBookByIdService(id);
    }

    @PutMapping("/change/{id}")
    public BookDTO updateBook(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
        return bookService.updateBookDTOService(id, bookDTO);
    }

}
