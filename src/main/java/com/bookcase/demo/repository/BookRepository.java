package com.bookcase.demo.repository;

import com.bookcase.demo.entity.Book;
import com.bookcase.demo.dto.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTitleStartsWith(String title);

    List<Book> findByCategory(BookCategory category);

    List<Book> findAllBooksByTitle(String title);
}
