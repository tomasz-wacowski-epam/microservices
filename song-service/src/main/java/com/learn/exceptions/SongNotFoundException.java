package com.learn.exceptions;

public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(String message) {

        super(message);
    }
}
