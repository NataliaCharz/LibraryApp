package com.bookcase.demo.mapper;

import com.bookcase.demo.entity.Author;
import com.bookcase.demo.model.AuthorDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    private AuthorMapper authorMapper;

    public static Author mapAuthorFromDTO (AuthorDTO authorDTO){
        return Author.builder()
                .name(authorDTO.getName())
                .surname(authorDTO.getSurname())
                .sex(authorDTO.getSex())
                .age(authorDTO.getAge())
                .alive(authorDTO.getAlive())
                .books(BookMapper.mapBookFromDTOList((authorDTO.getBookDTOS())))
                .build();
    }

    public static List<Author> mapAuthorFromDTOList(List <AuthorDTO> authorDTOS){
        return authorDTOS.stream()
                .map(AuthorMapper::mapAuthorFromDTO)
                .collect(Collectors.toList());
    }

    public static AuthorDTO mapAuthorToDTO (Author author){
        return AuthorDTO.builder()
                .name(author.getName())
                .surname(author.getSurname())
                .sex(author.getSex())
                .age(author.getAge())
                .alive(author.getAlive())
                //potrzeba zmapować book w author, bo inaczej zwróci samych autorów
                .bookDTOS(BookMapper.mapBookToDTOList(author.getBooks()))
                .build();
    }

    public static List<AuthorDTO> mapAuthorToDTOList(List <Author> authors){
        return authors.stream()
                .map(AuthorMapper::mapAuthorToDTO)
                .collect(Collectors.toList());
    }
}
