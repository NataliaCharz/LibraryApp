package com.bookcase.demo.controller;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.dto.BookDTO;
import com.bookcase.demo.mapper.AuthorMapperMapStruct;
import com.bookcase.demo.mapper.BookMapper;
import com.bookcase.demo.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/authors")
public class AuthorController {

    private final AuthorService authorService;
    @Qualifier("authorMapperMapStruct")
    private final AuthorMapperMapStruct authorMapper;
    private final BookMapper bookMapper;

    //wszyscy autorzy
    @GetMapping()
    public List<AuthorDTO> getAuthors(){
        return authorMapper.mapAuthorToDTOList(authorService.getAllAuthorsService());
    }

    //wszyscy autorzy z ksiazkami
    @GetMapping("/books")
    public List<AuthorDTO> getAuthorsWithBooks() {
            return authorMapper.mapAuthorToDTOList(authorService.getAuthorsWithBooksService());
    }
    //autor wyszukiwany po id
    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable(name = "id") Long id) {
        return authorMapper.mapAuthorToDTO(authorService.getAuthorByIdService(id));
    }

    @GetMapping("/get-books-by-author-id/{id}")
    public List<BookDTO> getBooksByAuthorId(@PathVariable(name = "id") Long id){
        return bookMapper.mapBookToDtoList(authorService.getBooksByAuthorId(id));
    }

    //ksiazki autora po nazwisku
    @GetMapping("/get-author-by-surname/{author}")
    public List<BookDTO> getBooksByAuthorSurname(@PathVariable(name = "author")String surname){
        return authorService.getBooksByAuthorSurname(surname);
    }

    //wszyscy autorzy ktorzy zyja lub nie
    @GetMapping("/alive")
    public List<AuthorDTO> getAuthorsDeadOrAlive(@RequestParam(name = "isAlive") Boolean isAlive){
        return authorMapper.mapAuthorToDTOList(authorService.getAuthorsDeadOrALiveService(isAlive));
    }

    //usun autora
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAuthorById(@PathVariable(name = "id") Long id) {
        authorService.deleteAuthorService(id);
    }

    //dodaj autora
    @PostMapping("/add")
    public void addNewAuthor(@RequestBody AuthorDTO authorDTO) {
        authorService.saveAuthorService(authorMapper.mapAuthorDTOtoAuthor(authorDTO));
    }

    //aktualizacja calego autora
    @PutMapping("/{id}")
    public AuthorDTO updateAuthorById(@RequestParam(name = "id") Long id, @RequestBody AuthorDTO authorDTO) {
        return authorService.updateAuthorService(id, authorDTO);
    }

    //aktualizacja czesciowa autora
    @PatchMapping("/{id}")
    public AuthorDTO partialUpdateAlive(@RequestParam(name="id") Long id, @RequestBody AuthorDTO authorDTO){
        return authorService.partialUpdateAuthorService(id, authorDTO);
    }


}

