package com.bookcase.demo.mapper;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.entity.Author;
import com.bookcase.demo.exception.InvalidDateOfBirthException;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {BookMapper.class})
public interface AuthorMapperMapStruct {
    @Mapping(source = ".", target = "age", qualifiedByName = "calculateAgeIfAuthorIsALive")
    @Mapping(source = "books", target = "bookDTOS")
    AuthorDTO mapAuthorToDTO(Author author);

    List<AuthorDTO> mapAuthorToDtoList(List<Author> authors);

    Author mapAuthorDTOtoAuthor(AuthorDTO authorDTO);

    @Named("calculateAge")
    default Integer calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        if (dateOfBirth.isAfter(currentDate)) {
            throw new InvalidDateOfBirthException(dateOfBirth);
        } else if (dateOfBirth == null) {
            throw new InvalidDateOfBirthException();
        } else {
            return Period.between(dateOfBirth, currentDate).getYears();
        }
    }

    @Named("calculateAgeIfAuthorIsALive")
    default Integer calculateAgeIfAuthorIsAlive(Author author) {
        if (!author.getAlive()) {
            return null;
        } else {
            return calculateAge(author.getDateOfBirth());
        }
    }

//    @AfterMapping
//    default void mapBooksToDto(Author author, @MappingTarget AuthorDTO authorDTO, @Context BookMapper bookMapper) {
//        if (author.getBooks() != null) {
//            List<BookDTO> bookDTOs = author.getBooks().stream()
//                    .map(bookMapper::mapBookToDto)
//                    .collect(Collectors.toList());
//            authorDTO.setBookDTOS(bookDTOs);
//        }
//    }

}


