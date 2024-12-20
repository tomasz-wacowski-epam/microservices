package com.learn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learn.exceptions.InvalidCsvException;
import com.learn.exceptions.SongAlreadyExistException;
import com.learn.exceptions.SongNotFoundException;
import com.learn.responses.ErrorResponseDTO;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(InvalidCsvException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCsvException(InvalidCsvException invalidCsvException) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(invalidCsvException.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleSongNotFoundException(SongNotFoundException songNotFoundException) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(songNotFoundException.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException() {

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(
                        "Wrong number format in csv file request parameter. Only comma separated positive integers allowed.",
                        HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Incorrect song metadata. Check \"details\" for explanation.",
                        HttpStatus.BAD_REQUEST.value(), errors));
    }

    @ExceptionHandler(SongAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceExistException(
            SongAlreadyExistException songAlreadyExistException) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(songAlreadyExistException.getMessage(), HttpStatus.CONFLICT.value()));
    }
}
