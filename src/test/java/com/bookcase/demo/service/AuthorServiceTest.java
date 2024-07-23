package com.bookcase.demo.service;

import com.bookcase.demo.dto.AuthorSex;
import com.bookcase.demo.entity.Author;
import com.bookcase.demo.exception.AuthorNotFoundException;
import com.bookcase.demo.repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class AuthorServiceTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;

    @BeforeEach
    public void setUp() {
        authorRepository.deleteAll();
        Author author1 = Author.builder().name("AA").surname("AA").sex(AuthorSex.MALE).alive(true).dateOfBirth(LocalDate.of(1999,02,23)).build();
        Author author2 = Author.builder().name("BB").surname("BB").sex(AuthorSex.FEMALE).alive(true).dateOfBirth(LocalDate.of(1987,05,6)).build();
        Author author3 = Author.builder().name("CC").surname("CC").sex(AuthorSex.MALE).alive(false).dateOfBirth(LocalDate.of(1976,03,17)).build();
        Author author4 = Author.builder().name("DD").surname("DD").sex(AuthorSex.MALE).alive(true).dateOfBirth(LocalDate.of(2002,12,2)).build();
        authorRepository.saveAndFlush(author1);
        authorRepository.saveAndFlush(author2);
        authorRepository.saveAndFlush(author3);
        authorRepository.saveAndFlush(author4);
    }

    @Test
    public void getAuthorByIdWillThrowExceptionWhenAuthorDoesNotExist() {
        //given
        int authorId = 90;

        //when
        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class,
                () -> authorService.getAuthorById(authorId));
        //then
        assertEquals("Author not found with id: 90",exception.getMessage());
    }

    @Test
    public void saveAuthorWillReturnNewListSize() {
        //given
        Author authorTest = Author.builder()
                .name("authorTest1")
                .surname("authorTest1")
                .sex(AuthorSex.MALE)
                .alive(true)
                .dateOfBirth(LocalDate.of(2000,1,1))
                .build();
        Author savedAuthor = authorRepository.saveAndFlush(authorTest);

        //when
        List<Author> authorListTest = authorRepository.findAll();

        //then
        assertEquals(authorListTest.size(), 5);
    }

}
