package com.bookcase.demo.mapper;

import com.bookcase.demo.entity.Author;
import com.bookcase.demo.dto.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorMapperForPartialUpdates {
    void map(AuthorDTO authorDTO, @MappingTarget Author author);

}
