package com.learn.exceptions;

public class InvalidCsvException extends RuntimeException {

    public InvalidCsvException(String message) {

        super(message);
    }
}
