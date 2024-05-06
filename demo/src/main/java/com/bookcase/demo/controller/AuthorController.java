package com.bookcase.demo.controller;

import com.bookcase.demo.entity.Author;
import com.bookcase.demo.mapper.AuthorMapper;
import com.bookcase.demo.model.AuthorDTO;
import com.bookcase.demo.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/all")
    public List<AuthorDTO> getAuthors(@RequestParam(name = "pageNum", required = false) Integer pageNum, Sort.Direction sort) {
//        int pageNumber = pageNum >= 0 ? pageNum : 0;
//        return AuthorMapper.mapAuthorToDTOList(authorService.getAuthorsByPage(pageNumber, sort).toList());
        if (pageNum == null) {
            return AuthorMapper.mapAuthorToDTOList(authorService.getAllAuthors());
        } else {
            return AuthorMapper.mapAuthorToDTOList(authorService.getAuthorsByPage(pageNum, sort).toList());
        }
    }

    @GetMapping("/all/books")
    public List<AuthorDTO> getAuthorsWithBooks(@RequestParam(name = "pageNum", required = false) Integer pageNum, Sort.Direction sort) {
        int pageNumber = pageNum != null ? pageNum : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        return AuthorMapper.mapAuthorToDTOList(authorService.getAuthorsWithBooksByPage(pageNumber, sortDirection).toList());
//        if (pageNum == null) {
//            return AuthorMapper.mapAuthorToDTOList(authorService.getAllAuthorsWithBooks());
//        } else {
//            return AuthorMapper.mapAuthorToDTOList(authorService.getAuthorsWithBooksByPage(pageNum, sort).toList());
//        }
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@RequestParam(name = "id") Integer id){
        return AuthorMapper.mapAuthorToDTO(authorService.getAuthorById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthorById(@RequestParam(name = "id") Integer id){
        authorService.deleteAuthor(id);
    }

    @PostMapping("/add")
    public void addNewAuthor(@RequestBody AuthorDTO authorDTO){
        authorService.createAuthor(AuthorMapper.mapAuthorFromDTO(authorDTO));
    }

    @PutMapping("/change/{id}")
    public AuthorDTO updateAuthorById(@RequestParam(name = "id") Integer id, @RequestBody AuthorDTO authorDTO){
        return authorService.updateAuthor(id, authorDTO);
    }
}
