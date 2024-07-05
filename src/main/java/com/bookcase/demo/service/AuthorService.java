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
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteAuthor(Integer id) {
        Author authorToDelete = getAuthorById(id);
        this.authorRepository.delete(authorToDelete);
    }

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
