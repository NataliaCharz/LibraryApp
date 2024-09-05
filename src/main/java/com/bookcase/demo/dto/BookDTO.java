package com.bookcase.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BookDTO {
    private Integer id;
    private String title;
    private Integer pages;
    private BookCategory category;
    private Boolean readBook;
    private Integer authorId;

    public BookDTO(Integer id, String title, int pages, BookCategory category, boolean readBook, Integer authorId) {
        this.id = id;
        this.title = title;
        this.pages = pages;
        this.category = category;
        this.readBook = readBook;
        this.authorId = authorId;
    }

}
