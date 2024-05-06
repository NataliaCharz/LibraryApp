package com.bookcase.demo.mapper;

import com.bookcase.demo.entity.Book;
import com.bookcase.demo.model.BookDTO;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookMapper {

    private BookMapper bookMapper;

    public static List<Book> mapBookFromDTOList(List<BookDTO> bookDTOS){
        return bookDTOS.stream()
                .map(BookMapper::mapBookFromDTO)
                .collect(Collectors.toList());
    }

    public static Book mapBookFromDTO(BookDTO bookDTO){
//        Book book = new Book();
//        book.setTitle(bookDTO.getTitle());
//        book.setPages(bookDTO.getPages());
//        book.setCategory(bookDTO.getCategory());
//        book.setReadBook(bookDTO.getReadBook());
//        return book;
        return Book.builder()
                .title(bookDTO.getTitle())
                .pages(bookDTO.getPages())
                .category(bookDTO.getCategory())
                .readBook(bookDTO.getReadBook())
                .build();
    }

    public static List<BookDTO> mapBookToDTOList(List<Book> books){
        return books.stream()
                .map(BookMapper::mapBookToDTO)
                .collect(Collectors.toList());
    }

    public static BookDTO mapBookToDTO(Book book){
//        BookDTO bookDTO = new BookDTO();
//        bookDTO.setTitle(book.getTitle());
//        bookDTO.setPages(book.getPages());
//        bookDTO.setCategory(book.getCategory());
//        bookDTO.setReadBook(book.getReadBook());
//        return bookDTO;
        return BookDTO.builder()
                .title(book.getTitle())
                .pages(book.getPages())
                .category(book.getCategory())
                .readBook(book.getReadBook())
                .build();
    }
}
