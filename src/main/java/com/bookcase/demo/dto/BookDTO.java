package com.bookcase.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BookDTO {
    private String title;
    private Integer pages;
    private BookCategory category;
    private Boolean readBook;

    public BookDTO(String title, int pages, BookCategory category, boolean readBook){
        this.title=title;
        this.pages=pages;
        this.category=category;
        this.readBook=readBook;
    }

}
