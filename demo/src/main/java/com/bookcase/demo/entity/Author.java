package com.bookcase.demo.entity;

import com.bookcase.demo.model.AuthorSex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Table(name="AUTHOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @SequenceGenerator(name="author_seq",sequenceName="author_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="author_seq")
    private Integer id;

    @Column(name="NAME")
    private String name;

    @Column(name="SURNAME")
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name="SEX")
    private AuthorSex sex;

    @Column(name="AGE")
    private Integer age;

    @Column(name="IS_ALIVE")
    private Boolean alive;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "author")
//    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//    @JoinColumn (name="authorId", updatable = false, insertable = false)
    private List<Book> books;

}
