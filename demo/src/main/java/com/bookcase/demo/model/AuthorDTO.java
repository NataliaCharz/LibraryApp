package com.bookcase.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BookDTO> bookDTOS;

}
