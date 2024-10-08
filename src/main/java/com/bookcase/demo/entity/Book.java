package com.bookcase.demo.entity;

import com.bookcase.demo.dto.BookCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Table (name="BOOK")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @SequenceGenerator(name="book_id_seq", sequenceName="book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="TITLE", nullable = false)
    private String title;

    @Column(name="PAGES")
    private Integer pages;

    @Enumerated(EnumType.STRING)
    @Column(name="CATEGORY")
    private BookCategory category;

    @Column(name="READ_BOOK")
    private Boolean readBook;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="authorId")
    private Author author;

}
