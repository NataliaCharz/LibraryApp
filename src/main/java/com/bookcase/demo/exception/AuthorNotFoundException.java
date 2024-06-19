package com.bookcase.demo.exception;

public class AuthorNotFoundException extends RuntimeException{

    private static String ERROR_MESSAGE = "Author not found with id: ";

    public AuthorNotFoundException(Integer id){
        super(ERROR_MESSAGE + id);
    }

}
