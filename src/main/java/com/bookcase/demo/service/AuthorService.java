package com.bookcase.demo.service;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.entity.Author;
import com.bookcase.demo.exception.AuthorNotFoundException;
import com.bookcase.demo.mapper.AuthorMapperMapStruct;
import com.bookcase.demo.mapper.AuthorMapperForPartialUpdates;
import com.bookcase.demo.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapperForPartialUpdates authorMapperForPartialUpdates;
    private final AuthorMapperMapStruct authorMapper;


    //wszyscy autorzy
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }


    public List<Author> getAuthorsWithBooks() {
        return this.authorRepository.findAllWithBooks();
    }

    public Author getAuthorById(Integer id) throws AuthorNotFoundException {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    //autorzy, którzy żyją lub nie żyją
    public List<Author> getAuthorsDeadOrALive(Boolean isALive){
        List<Author> allAuthors = this.authorRepository.findAll();
        return allAuthors.stream()
                .filter(author -> author.getAlive().equals(isALive))
                .toList();
    }

    //usuń autora
    public void deleteAuthor(Integer id) {
        Author authorToDelete = getAuthorById(id);
        this.authorRepository.delete(authorToDelete);
    }

    //zapisz autora
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAuthor(Author author) {
        this.authorRepository.save(author);
    }

    public AuthorDTO updateAuthor(Integer id, AuthorDTO authorDTO) {
        Author authorToUpdate = getAuthorById(id);
        authorMapper.mapAuthorDTOToAuthorInMemory(authorDTO, authorToUpdate);
        this.authorRepository.save(authorToUpdate);
        return authorMapper.mapAuthorToDTO(authorToUpdate);
    }

    @Transactional
    public AuthorDTO partialUpdateAuthor(Integer id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        log.info("Author before update: {}", author);

        authorMapperForPartialUpdates.map(authorDTO, author);

        log.info("Author after update: {}", author);

        return authorMapper.mapAuthorToDTO(author);
    }

}
