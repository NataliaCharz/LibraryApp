package com.bookcase.demo.controller;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.dto.BookCategory;
import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("/search")
    public List<BookDTO> getBooks(@RequestParam String character) {
        return bookService.getAllBooksStartsByCharacter(character)
                .stream()
                .map(BookMapper::mapBookToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<BookDTO> getBooksByPage(@RequestParam (name = "pageNum", required = false) Integer pageNum, Sort.Direction sort){
        if(pageNum == null){
            return BookMapper.mapBookToDTOList(bookService.getAllBooks());
        } else {
            return BookMapper.mapBookToDTOList(bookService.getBooksByPage(pageNum, sort).toList());
        }
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable(name = "id") Integer id) {
        return BookMapper.mapBookToDTO(bookService.getById(id));
    }

    @GetMapping("/author")
    public List<AuthorDTO> getBookAuthor(@RequestParam String title){
        return this.bookService.getBookAuthor(title);
    }
    
    @GetMapping("/find-by-category/{category}")
    public List<BookDTO> getBookByCategory(@PathVariable(name = "category")BookCategory category){
        return bookService.getByCategory(category).stream()
                .map(BookMapper::mapBookToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public void addNewBook(@RequestBody BookDTO bookDTO, @RequestParam Integer authorId){
        bookService.createNewBook(BookMapper.mapBookFromDTO(bookDTO),authorId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBookFromBookcase(@PathVariable("id") Integer id){
        bookService.deleteBookById(id);
    }

    @PutMapping("/change/{id}")
    public BookDTO updateBook(@PathVariable("id") Integer id, @RequestBody BookDTO bookDTO){
        return bookService.updateBookDTO(id, bookDTO);
    }

}
