package com.bookcase.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LibraryTitle {
    @JsonProperty("title")
    private String bookTitle;
    @JsonProperty("author_name")
    private String[] authorName;
}
