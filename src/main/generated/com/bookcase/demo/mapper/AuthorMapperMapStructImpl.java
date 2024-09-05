package com.bookcase.demo.mapper;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.entity.Author;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-05T17:52:45+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AuthorMapperMapStructImpl implements AuthorMapperMapStruct {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public AuthorDTO mapAuthorToDTO(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDTO.AuthorDTOBuilder authorDTO = AuthorDTO.builder();

        authorDTO.age( calculateAgeIfAuthorIsAlive( author ) );
        authorDTO.bookDTOS( bookMapper.mapBookToDtoList( author.getBooks() ) );
        authorDTO.name( author.getName() );
        authorDTO.surname( author.getSurname() );
        authorDTO.sex( author.getSex() );
        authorDTO.dateOfBirth( author.getDateOfBirth() );
        authorDTO.alive( author.getAlive() );

        return authorDTO.build();
    }

    @Override
    public void mapAuthorDTOToAuthorInMemory(AuthorDTO authorDTO, Author author) {
        if ( authorDTO == null ) {
            return;
        }

        author.setName( authorDTO.getName() );
        author.setSurname( authorDTO.getSurname() );
        author.setSex( authorDTO.getSex() );
        author.setDateOfBirth( authorDTO.getDateOfBirth() );
        author.setAlive( authorDTO.getAlive() );
    }

    @Override
    public List<AuthorDTO> mapAuthorToDTOList(List<Author> authors) {
        if ( authors == null ) {
            return null;
        }

        List<AuthorDTO> list = new ArrayList<AuthorDTO>( authors.size() );
        for ( Author author : authors ) {
            list.add( mapAuthorToDTO( author ) );
        }

        return list;
    }

    @Override
    public Author mapAuthorDTOtoAuthor(AuthorDTO authorDTO) {
        if ( authorDTO == null ) {
            return null;
        }

        Author.AuthorBuilder author = Author.builder();

        author.name( authorDTO.getName() );
        author.surname( authorDTO.getSurname() );
        author.sex( authorDTO.getSex() );
        author.dateOfBirth( authorDTO.getDateOfBirth() );
        author.alive( authorDTO.getAlive() );

        return author.build();
    }
}
