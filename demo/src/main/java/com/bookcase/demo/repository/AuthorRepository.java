package com.bookcase.demo.repository;

import com.bookcase.demo.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(value = "SELECT author FROM Author author")
    List<Author> findAll();

    @Query("""
            SELECT author FROM Author author
            LEFT JOIN FETCH author.books
            
            """)
    List<Author> findAllWithBooks();
    @Query("""
            SELECT author FROM Author author
            LEFT JOIN FETCH author.books
            """)
    Page<Author> findAllWithBooksByPage(Pageable pageable);
}
