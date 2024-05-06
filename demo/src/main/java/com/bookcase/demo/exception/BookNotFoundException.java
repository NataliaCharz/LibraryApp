package com.bookcase.demo.exception;

public class BookNotFoundException extends RuntimeException {

    private static String ERROR_MESSAGE = "No book found";

    public BookNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
