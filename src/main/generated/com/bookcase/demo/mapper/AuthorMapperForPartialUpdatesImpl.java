package com.bookcase.demo.mapper;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.entity.Author;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-12T16:15:28+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AuthorMapperForPartialUpdatesImpl implements AuthorMapperForPartialUpdates {

    @Override
    public void map(AuthorDTO authorDTO, Author author) {
        if ( authorDTO == null ) {
            return;
        }

        if ( authorDTO.getName() != null ) {
            author.setName( authorDTO.getName() );
        }
        if ( authorDTO.getSurname() != null ) {
            author.setSurname( authorDTO.getSurname() );
        }
        if ( authorDTO.getSex() != null ) {
            author.setSex( authorDTO.getSex() );
        }
        if ( authorDTO.getDateOfBirth() != null ) {
            author.setDateOfBirth( authorDTO.getDateOfBirth() );
        }
        if ( authorDTO.getAlive() != null ) {
            author.setAlive( authorDTO.getAlive() );
        }
    }
}
