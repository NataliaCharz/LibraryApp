package com.bookcase.demo.controller;

import com.bookcase.demo.dto.AuthorDTO;
import com.bookcase.demo.mapper.AuthorMapperMapStruct;
import com.bookcase.demo.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapperMapStruct authorMapper;

    //wszyscy autorzy bez stron i ze stronami
    @GetMapping()
    public List<AuthorDTO> getAuthors(@RequestParam(name = "pageNum", required = false) Integer pageNum, Sort.Direction sort, Model model) {
//        model.addAllAttributes("name", Author.)
        if (pageNum == null) {
            return authorMapper.mapAuthorToDtoList(authorService.getAllAuthors());
        } else {
            return authorMapper.mapAuthorToDtoList(authorService.getAuthorsByPage(pageNum, sort).toList());
        }
//        return "authors";
    }

    //wszyscy autorzy z ksiazkami bez stron i ze stronami
    @GetMapping("/books")
    public List<AuthorDTO> getAuthorsWithBooks(@RequestParam(name = "pageNum", required = false) Integer pageNum, Sort.Direction sort) {
        if (pageNum == null) {
            return authorMapper.mapAuthorToDtoList(authorService.getAuthorsWithBooks());
        } else {
            return authorMapper.mapAuthorToDtoList(authorService.getAuthorsWithBooksByPage(pageNum, sort).toList());
        }
    }

    //autor wyszukiwany po id
    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable(name = "id") Integer id) {
        return authorMapper.mapAuthorToDTO(authorService.getAuthorById(id));
    }

    //wszyscy autorzy ktorzy zyja lub nie
    @GetMapping("/alive")
    public List<AuthorDTO> getAuthorsDeadOrAlive(@RequestParam(name = "isAlive") Boolean isAlive){
        return authorMapper.mapAuthorToDtoList(authorService.getAuthorsDeadOrALive(isAlive));
    }

    @DeleteMapping("/{id}")
    public void deleteAuthorById(@PathVariable(name = "id") Integer id) {
        authorService.deleteAuthor(id);
    }

    @PostMapping()
    public void addNewAuthor(@RequestBody AuthorDTO authorDTO) {
        authorService.saveAuthor(authorMapper.mapAuthorDTOtoAuthor(authorDTO));
    }

    @PutMapping("/{id}")
    public AuthorDTO updateAuthorById(@RequestParam(name = "id") Integer id, @RequestBody AuthorDTO authorDTO) {
        return authorService.updateAuthor(id, authorDTO);
    }

    @PatchMapping("/{id}")
    public AuthorDTO partialUpdateAlive2(@RequestParam(name="id") Integer id, @RequestBody AuthorDTO authorDTO){
        return authorService.partialUpadateAuthor(id, authorDTO);
    }


}
