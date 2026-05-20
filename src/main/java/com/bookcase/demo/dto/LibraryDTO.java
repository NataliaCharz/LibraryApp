package com.bookcase.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class LibraryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("docs")
    List<LibraryTitle> bookTitlelist;

}
