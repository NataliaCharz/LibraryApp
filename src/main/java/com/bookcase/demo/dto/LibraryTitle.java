package com.bookcase.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LibraryTitle {
    @JsonProperty("title")
    public String bookTitle;
}
