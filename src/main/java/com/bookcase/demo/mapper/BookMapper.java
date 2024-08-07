package com.bookcase.demo.mapper;

import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    BookDTO mapBookToDto(Book book);
    Book mapBookFromDto(BookDTO bookDTO);
    List<BookDTO> mapBookToDtoList(List<Book> books);

}
