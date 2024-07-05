package com.bookcase.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class LibraryDTO {
    @JsonProperty("docs")
    List<LibraryTitle> bookTitlelist;

}
