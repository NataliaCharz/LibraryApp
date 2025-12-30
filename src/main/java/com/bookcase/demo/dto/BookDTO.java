package com.bookcase.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BookDTO {
    private Long bookId;
    private String title;
    private Integer pages;
    private BookCategory category;
    private Boolean readBook;
    private Long authorId;

    public BookDTO(Long id, String title, int pages, BookCategory category, boolean readBook, Long authorId) {
        this.bookId = id;
        this.title = title;
        this.pages = pages;
        this.category = category;
        this.readBook = readBook;
        this.authorId = authorId;
    }

}
