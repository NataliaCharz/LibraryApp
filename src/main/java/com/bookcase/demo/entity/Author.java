package com.bookcase.demo.entity;

import com.bookcase.demo.dto.AuthorSex;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Table(name="AUTHOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author {

    @Id
    @SequenceGenerator(name="author_id_seq",sequenceName="author_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "author_id_seq")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="SURNAME")
    private String surname;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name="SEX", columnDefinition = "sex")
    private AuthorSex sex;

    @Column(name="DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    @Column(name="IS_ALIVE")
    private Boolean alive;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "author")
    private List<Book> books;

}
