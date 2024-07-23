package com.bookcase.demo.mapper;

import com.bookcase.demo.entity.Author;
import com.bookcase.demo.dto.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorMapperForPartialUpdates {
    @Autowired
    void map(AuthorDTO authorDTO, @MappingTarget Author author);

}
