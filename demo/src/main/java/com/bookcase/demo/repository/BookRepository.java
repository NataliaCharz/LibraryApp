package com.bookcase.demo.repository;

import com.bookcase.demo.entity.Book;
import com.bookcase.demo.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByTitleStartsWith(String title);

    List<Book> findAllByAuthorIdIn(List<Integer> ids);

    List<Book> findByCategory(BookCategory category);
}
