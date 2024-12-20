package com.learn.exceptions;

public class SongAlreadyExistException extends RuntimeException {

    public SongAlreadyExistException(String message) {

        super(message);
    }
}
