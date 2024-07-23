package com.bookcase.demo.service;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.entity.Author;
import com.bookcase.demo.exception.AuthorNotFoundException;
import com.bookcase.demo.mapper.AuthorMapper;
import com.bookcase.demo.mapper.AuthorMapperForPartialUpdates;
import com.bookcase.demo.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private static final int page_Size = 20;


    //wszyscy autorzy
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    //wszyscy autorzy ze stronami
    public Page<Author> getAuthorsByPage(int page, Sort.Direction sort) {
        return this.authorRepository.findAll(PageRequest.of(page, page_Size, Sort.by(sort, "id")));
    }

    public List<Author> getAuthorsWithBooks() {
        return this.authorRepository.findAllWithBooks();
    }

    public Page<Author> getAuthorsWithBooksByPage(int page, Sort.Direction sort) {
        return this.authorRepository.findAllWithBooksByPage(PageRequest.of(page, page_Size, Sort.by(sort, "id")));
    }

    public Author getAuthorById(Integer id) throws AuthorNotFoundException {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    //autorzy, którzy żyją lub nie żyją
    public List<Author> getAuthorsDeadOrALive(Boolean isALive){
        List<Author> allAuthors = this.authorRepository.findAll();
        List<Author> authorsIsAlive = allAuthors.stream()
                .filter(author -> author.getAlive().equals(isALive))
                .collect(Collectors.toList());
        return authorsIsAlive;
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
        authorToUpdate = AuthorMapper.mapAuthorWithBookFromDTO(authorDTO);
        this.authorRepository.save(authorToUpdate);
        return AuthorMapper.mapAuthorWithBookToDTO(authorToUpdate);
    }

    @Transactional
    public AuthorDTO partialUpadateAuthor(Integer id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id.intValue()).orElseThrow(() -> new AuthorNotFoundException(id));
        log.info("Author before update: {}", author);

        authorMapperForPartialUpdates.map(authorDTO, author);

        log.info("Author after update: {}", author);

        return AuthorMapper.mapAuthorToDTO(author);
    }

}
