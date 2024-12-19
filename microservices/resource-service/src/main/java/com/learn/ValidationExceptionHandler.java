package com.learn;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learn.exceptions.InvalidCsvException;
import com.learn.exceptions.ResourceNotFoundException;
import com.learn.exceptions.WrongIdException;
import com.learn.responses.ErrorResponseDTO;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(InvalidCsvException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCsvException(InvalidCsvException invalidCsvException) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(invalidCsvException.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMediaTypeNotSupportedException.class,
            TypeMismatchException.class, NumberFormatException.class, WrongIdException.class})
    public ResponseEntity<ErrorResponseDTO> handleValidationException() {

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO("Bad request", HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceExistException() {

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


}
