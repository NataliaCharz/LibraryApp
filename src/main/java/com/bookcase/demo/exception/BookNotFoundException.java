package com.bookcase.demo.exception;

public class BookNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Book not found";

    public BookNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
