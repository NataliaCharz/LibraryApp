package com.bookcase.demo.service;

import com.bookcase.demo.entity.Author;
import com.bookcase.demo.entity.Book;
import com.bookcase.demo.exception.AuthorNotFoundException;
import com.bookcase.demo.mapper.AuthorMapper;
import com.bookcase.demo.model.AuthorDTO;
import com.bookcase.demo.repository.AuthorRepository;
import com.bookcase.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private static final int page_Size = 20;

    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    public List<Author> getAllAuthorsWithBooks() {
        List<Author> allAuthors = this.authorRepository.findAll();
        List<Integer> ids = allAuthors.stream()
                .map(Author::getId)
                .collect(Collectors.toList());
        List<Book> booksByAuthor = bookRepository.findAllByAuthorIdIn(ids);
        allAuthors.forEach(author -> author.setBooks(extractBook(booksByAuthor, author.getId())));
        return allAuthors;
    }

    private List<Book> extractBook(List<Book> booksByAuthor, int id) {
        return booksByAuthor.stream()
                .filter(book -> book.getAuthorId() == id)
                .collect(Collectors.toList());
    }

    public Page<Author> getAuthorsByPage(int page, Sort.Direction sort) {
        return this.authorRepository.findAll(PageRequest.of(page, page_Size, Sort.by(sort, "id")));
    }

    public Page<Author> getAuthorsWithBooksByPage(int page, Sort.Direction sort) {
        Page<Author> allAuthors = this.authorRepository.findAll(PageRequest.of(page, page_Size, Sort.by(sort, "id")));
        List<Integer> ids = allAuthors.stream()
                .map(Author::getId)
                .collect(Collectors.toList());
        List<Book> booksByAuthor = bookRepository.findAllByAuthorIdIn(ids);
        allAuthors.forEach(author -> author.setBooks(extractBook(booksByAuthor, author.getId())));
        return (Page<Author>) allAuthors;
    }

    public Author getAuthorById(Integer id) throws AuthorNotFoundException {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found on::" + id));
    }

    public void deleteAuthor(Integer id) {
        Author authorToDelete = getAuthorById(id);
        this.authorRepository.delete(authorToDelete);
    }

    public void createAuthor(Author author) {
        this.authorRepository.save(author);
    }


    public AuthorDTO updateAuthor(Integer id, AuthorDTO authorDTO) {
        Author authorToUpdate = getAuthorById(id);
        authorToUpdate = AuthorMapper.mapAuthorFromDTO(authorDTO);
        this.authorRepository.save(authorToUpdate);
        return AuthorMapper.mapAuthorToDTO(authorToUpdate);
    }
}
