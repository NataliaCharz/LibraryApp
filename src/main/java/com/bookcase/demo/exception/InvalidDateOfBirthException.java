package com.bookcase.demo.exception;

import java.time.LocalDate;

public class InvalidDateOfBirthException extends RuntimeException {

    private static String INVALID_DATE_ERROR_MESSAGE = "Invalid date of birth: %s";
    private static String DATE_IS_NULL_ERROR_MESSAGE = "There is no date of birth";


    public InvalidDateOfBirthException() {
        super(DATE_IS_NULL_ERROR_MESSAGE);
    }

    public InvalidDateOfBirthException(LocalDate dateOfBirth) {
        super(String.format(INVALID_DATE_ERROR_MESSAGE, dateOfBirth));
    }

}
