package com.bookcase.demo.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "User not found";

    public UserNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public UserNotFoundException(Long id) {
        super(ERROR_MESSAGE + " with id: " + id);
    }
}
