package com.bookcase.demo.mapper;

import com.bookcase.demo.entity.Author;
import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.exception.InvalidDateOfBirthException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    private AuthorMapper authorMapper;

    public static Author mapAuthorWithBookFromDTO(AuthorDTO authorDTO) {
        return Author.builder()
                .name(authorDTO.getName())
                .surname(authorDTO.getSurname())
                .sex(authorDTO.getSex())
                .dateOfBirth(authorDTO.getDateOfBirth())
                .alive(authorDTO.getAlive())
                .books(BookMapper.mapBookFromDTOList((authorDTO.getBookDTOS())))
                .build();
    }

    public static List<Author> mapAuthorWithBookFromDTOList(List<AuthorDTO> authorDTOS) {
        return authorDTOS.stream()
                .map(AuthorMapper::mapAuthorWithBookFromDTO)
                .collect(Collectors.toList());
    }

    public static Author mapAuthorFromDTO(AuthorDTO authorDTO) {
        return Author.builder()
                .name(authorDTO.getName())
                .surname(authorDTO.getSurname())
                .sex(authorDTO.getSex())
                .dateOfBirth(authorDTO.getDateOfBirth())
                .alive(authorDTO.getAlive())
                .build();
    }

    public static List<Author> mapAuthorFromDTOList(List<AuthorDTO> authorDTOS) {
        return authorDTOS.stream()
                .map(AuthorMapper::mapAuthorFromDTO)
                .collect(Collectors.toList());
    }

    public static AuthorDTO mapAuthorWithBookToDTO(Author author) {
        return AuthorDTO.builder()
                .name(author.getName())
                .surname(author.getSurname())
                .sex(author.getSex())
                .dateOfBirth(author.getDateOfBirth())
                .alive(author.getAlive())
                .age(author.getAlive() ? calculateAge(author.getDateOfBirth()) : null)
                .bookDTOS(BookMapper.mapBookToDTOList(author.getBooks()))
                .build();
    }

    public static List<AuthorDTO> mapAuthorWithBookToDTOList(List<Author> authors) {
        return authors.stream()
                .map(AuthorMapper::mapAuthorWithBookToDTO)
                .collect(Collectors.toList());
    }

    public static AuthorDTO mapAuthorToDTO(Author author) {
        return AuthorDTO.builder()
                .name(author.getName())
                .surname(author.getSurname())
                .sex(author.getSex())
                .dateOfBirth(author.getDateOfBirth())
                .alive(author.getAlive())
                .age(author.getAlive() ? calculateAge(author.getDateOfBirth()) : null)
                .build();
    }

    public static List<AuthorDTO> mapAuthorToDTOList(List<Author> authors) {
        return authors.stream()
                .map(AuthorMapper::mapAuthorToDTO)
                .collect(Collectors.toList());
    }

    private static int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        if (dateOfBirth.isAfter(currentDate)) {
            throw new InvalidDateOfBirthException(dateOfBirth);
        } else if (dateOfBirth == null) {
            throw new InvalidDateOfBirthException();
        } else {
            return Period.between(dateOfBirth, currentDate).getYears();
        }
    }
}
