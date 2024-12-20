package com.learn;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learn.exceptions.InvalidCsvException;
import com.learn.exceptions.ParseResourceException;
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

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleWrongMediaTypeException() {

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO("Wrong media type in the request body. Only audio/mpeg files allowed.",
                        HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatchException(TypeMismatchException typeMismatchException) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(
                        "Wrong number format used for resource ID = \"" + typeMismatchException.getValue()
                                + "\". Only positive integers allowed.",
                        HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleNumberFormatException(NumberFormatException numberFormatException) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(
                        "Wrong number format in csv file request parameter. Only comma separated positive integers allowed.",
                        HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(WrongIdException.class)
    public ResponseEntity<ErrorResponseDTO> handleWrongIdException(WrongIdException wrongIdException) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(wrongIdException.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ParseResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleParseResourceException(
            ParseResourceException parseResourceException) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(parseResourceException.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceExistException() {

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


}
