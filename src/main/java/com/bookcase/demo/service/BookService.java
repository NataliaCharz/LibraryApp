package com.bookcase.demo.service;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.entity.Author;
import com.bookcase.demo.entity.Book;
import com.bookcase.demo.exception.AuthorNotFoundException;
import com.bookcase.demo.exception.BookNotFoundException;
import com.bookcase.demo.mapper.AuthorMapperMapStruct;
import com.bookcase.demo.dto.BookCategory;
import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.repository.AuthorRepository;
import com.bookcase.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorMapperMapStruct authorMapper;
    private final BookMapper bookMapper;
    private static final int PAGE_SIZE = 20;

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book getById(Integer id) {
        Optional<Book> bookFoundById = this.bookRepository.findById(id);
        if (!bookFoundById.isPresent()) {
            log.info("There is no book with id: {}", id);
            throw new BookNotFoundException();
        } else {
            return bookFoundById.get();
        }
    }

    public List<Book> getAllBooksStartsByCharacter(String character) {
        log.info("Received character: {}", character);
        List<Book> bookList = bookRepository.findAll();
        List<Book> fitList = new ArrayList<>();

        bookList.forEach(book -> {
            if (book.getTitle().toLowerCase().startsWith(character.toLowerCase())) {
                fitList.add(book);
            }
        });
        return fitList;
    }

    public void createNewBook(Book bookToSave, Integer authorId) {
        Optional<Author> author = this.authorRepository.findById(authorId);
        if (author.isPresent()) {
            bookToSave.setAuthor(author.get());
            this.bookRepository.save(bookToSave);
        } else {
            throw new AuthorNotFoundException(authorId);
        }
    }

    public void deleteBookById(Integer id) {
        Optional<Book> bookById = this.bookRepository.findById(id);
        if (!bookById.isPresent()) {
            log.info("There is no book with id: {}", id);
            throw new BookNotFoundException();
        }
        Book bookToDelete = bookById.get();
        this.bookRepository.delete(bookToDelete);
        log.info("Book's been successfully removed");
    }

    public BookDTO updateBookDTO(Integer id, BookDTO bookDTO) {
        Optional<Book> bookToUpdateOptional = this.bookRepository.findById(id);
        if (bookToUpdateOptional.isEmpty()) {
            log.info("Book with this id: {} do not exist.", id);
            throw new BookNotFoundException();
        }
        Book bookToUpdate = bookToUpdateOptional.get();
        bookToUpdate = bookMapper.mapBookFromDto(bookDTO);
        this.bookRepository.save(bookToUpdate);
        return bookMapper.mapBookToDto(bookToUpdate);
    }

    public List<Book> getByCategory(BookCategory category) {
        return this.bookRepository.findByCategory(category);
    }


    public List<AuthorDTO> getBookAuthor(String title) {
        List<Book> bookList = this.bookRepository.findAllBooksByTitle(title);
        List<Author> authorList = bookList.stream()
                .map(Book::getAuthor)
                .collect(Collectors.toList());
        return authorMapper.mapAuthorToDTOList(authorList);
    }
}
