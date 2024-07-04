package com.bookcase.demo.repository;

import com.bookcase.demo.dto.AuthorSex;
import com.bookcase.demo.entity.Author;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {
        authorRepository.deleteAll();
    }

    @BeforeEach
    public void createTestAuthors(){
        Author author1 = Author.builder().name("AA").surname("AA").sex(AuthorSex.MALE).alive(true).age(20).build();
        Author author2 = Author.builder().name("BB").surname("BB").sex(AuthorSex.FEMALE).alive(true).age(21).build();
        Author author3 = Author.builder().name("CC").surname("CC").sex(AuthorSex.MALE).alive(false).age(22).build();
        Author author4 = Author.builder().name("DD").surname("DD").sex(AuthorSex.MALE).alive(true).age(23).build();
        authorRepository.saveAndFlush(author1);
        authorRepository.saveAndFlush(author2);
        authorRepository.saveAndFlush(author3);
        authorRepository.saveAndFlush(author4);
    }

    @Test
    public void findAllWillResultExpectedListSize(){
        //given

        //when
        List<Author> authorList = authorRepository.findAll();

        //then
        assertEquals(authorList.size(), 4);
    }
}
