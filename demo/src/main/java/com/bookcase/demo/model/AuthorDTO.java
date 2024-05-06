package com.bookcase.demo.model;

import com.bookcase.demo.entity.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private String name;
    private String surname;
    private AuthorSex sex;
    private Integer age;
    private Boolean alive;
    private List<BookDTO> bookDTOS;

}
