package com.bookcase.demo.exception;

public class AuthorNotFoundException extends RuntimeException{

    private static String ERROR_MESSAGE = "Author not found with id: ";

    public AuthorNotFoundException(Long id){
        super(ERROR_MESSAGE + id);
    }

    public AuthorNotFoundException(String surname){
        super(ERROR_MESSAGE + surname);
    }

}
