package com.bookcase.demo.service;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.entity.Author;
import com.bookcase.demo.entity.Book;
import com.bookcase.demo.exception.AuthorNotFoundException;
import com.bookcase.demo.mapper.AuthorMapperMapStruct;
import com.bookcase.demo.mapper.AuthorMapperForPartialUpdates;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapperForPartialUpdates authorMapperForPartialUpdates;
    @Qualifier("authorMapperMapStruct")
    private final AuthorMapperMapStruct authorMapper;
    private final BookMapper bookMapper;


    //wszyscy autorzy
    public List<Author> getAllAuthorsService() {
        return this.authorRepository.findAll();
    }


    //wszyscy autorzy z ksiazkami
    public List<Author> getAuthorsWithBooksService() {
        return this.authorRepository.findAllWithBooks();
    }

    //autorzy, których nazwisko zawiera podany ciąg liter
    public List<Author> getAuthorsContainingCharactersInSurnameService(String character) {
        return this.authorRepository.findAll().stream()
                .filter(author -> author.getSurname().toLowerCase().contains(character.toLowerCase()))
                .collect(Collectors.toList());
    }

    //autor po id
    public Author getAuthorByIdService(Long id) throws AuthorNotFoundException {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    //ksiazki wyszukane po nazwisku autora
    public List<BookDTO> getBooksByAuthorSurname(String surname){
        List<Author> authorList = this.authorRepository.findBySurnameContainingIgnoreCase(surname);
        List<Book> bookList = authorList.stream()
                .flatMap(author -> author.getBooks().stream())
                .collect(Collectors.toList());
        return bookMapper.mapBookToDtoList(bookList);
    }

    //autorzy, którzy żyją lub nie żyją
    public List<Author> getAuthorsDeadOrALiveService(Boolean isALive){
        List<Author> allAuthors = this.authorRepository.findAll();
        return allAuthors.stream()
                .filter(author -> author.getAlive().equals(isALive))
                .toList();
    }

    //usuń autora
    public void deleteAuthorService(Long id) {
        Author authorToDelete = getAuthorByIdService(id);
        this.authorRepository.delete(authorToDelete);
    }

    //dodaj autora
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAuthorService(Author author) {
        this.authorRepository.save(author);
    }

    //aktualizacja calego autora
    public AuthorDTO updateAuthorService(Long id, AuthorDTO authorDTO) {
        Author authorToUpdate = getAuthorByIdService(id);
        authorMapper.mapAuthorDTOToAuthorInMemory(authorDTO, authorToUpdate);
        this.authorRepository.save(authorToUpdate);
        return authorMapper.mapAuthorToDTO(authorToUpdate);
    }

    //aktualizacja czesciowa autora
    @Transactional
    public AuthorDTO partialUpdateAuthorService(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        log.info("Author before update: {}", author);

        authorMapperForPartialUpdates.map(authorDTO, author);

        log.info("Author after update: {}", author);

        return authorMapper.mapAuthorToDTO(author);
    }

    public List<Book> getBooksByAuthorId(Long authorId) {
        return authorRepository.findByIdWithBooks(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId))
                .getBooks();
    }
}

