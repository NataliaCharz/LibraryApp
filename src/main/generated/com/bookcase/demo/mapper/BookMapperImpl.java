package com.bookcase.demo.mapper;

import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.entity.Book;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-12T16:15:28+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO mapBookToDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO.BookDTOBuilder bookDTO = BookDTO.builder();

        bookDTO.title( book.getTitle() );
        bookDTO.pages( book.getPages() );
        bookDTO.category( book.getCategory() );
        bookDTO.readBook( book.getReadBook() );

        return bookDTO.build();
    }

    @Override
    public Book mapBookFromDto(BookDTO bookDTO) {
        if ( bookDTO == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.title( bookDTO.getTitle() );
        book.pages( bookDTO.getPages() );
        book.category( bookDTO.getCategory() );
        book.readBook( bookDTO.getReadBook() );

        return book.build();
    }

    @Override
    public List<BookDTO> mapBookToDtoList(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookDTO> list = new ArrayList<BookDTO>( books.size() );
        for ( Book book : books ) {
            list.add( mapBookToDto( book ) );
        }

        return list;
    }
}
