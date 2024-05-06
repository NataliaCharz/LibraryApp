package com.bookcase.demo.exception;

public class AuthorNotFoundException extends RuntimeException{

    private static String ERROR_MESSAGE = "Author not found";

    public AuthorNotFoundException(){
        super(ERROR_MESSAGE);
    }

    public AuthorNotFoundException(String message){
        super(message);
    }

}
