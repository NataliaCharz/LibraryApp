package com.bookcase.demo.service;

import com.bookcase.demo.dto.AuthorSex;
import com.bookcase.demo.entity.Author;
import com.bookcase.demo.repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class AuthorServiceTest {
    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {
        authorRepository.deleteAll();
        Author author1 = Author.builder().name("AA").surname("AA").sex(AuthorSex.MALE).alive(true).age(20).build();
        Author author2 = Author.builder().name("BB").surname("BB").sex(AuthorSex.FEMALE).alive(true).age(21).build();
        Author author3 = Author.builder().name("CC").surname("CC").sex(AuthorSex.MALE).alive(false).age(22).build();
        Author author4 = Author.builder().name("DD").surname("DD").sex(AuthorSex.MALE).alive(true).age(23).build();
        authorRepository.saveAndFlush(author1);
        authorRepository.saveAndFlush(author2);
        authorRepository.saveAndFlush(author3);
        authorRepository.saveAndFlush(author4);
    }

//    @Test
//    public void
    @Test
    public void saveAuthorWillReturnNewListSize(){
        //given
        Author authorTest = Author.builder()
                .name("authorTest1")
                .surname("authorTest1")
                .sex(AuthorSex.MALE)
                .alive(true)
                .age(24)
                .build();
        Author savedAuthor = authorRepository.saveAndFlush(authorTest);

        //when
        List<Author> authorListTest = authorRepository.findAll();

        //then
        assertEquals(authorListTest.size(),5);
    }

}
