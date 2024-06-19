package com.bookcase.demo.advice;

import com.bookcase.demo.exception.AuthorNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AuthorNotFoundAdvice {

//    @ExceptionHandler(AuthorNotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String authorNotFoundHandler(AuthorNotFoundException ex){
//        return ex.getMessage();
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception ex){
        log.error("Unexpected error.", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
