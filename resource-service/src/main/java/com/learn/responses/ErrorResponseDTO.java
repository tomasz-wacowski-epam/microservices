package com.learn.responses;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(String errorMessage, int errorCode, Map<String, String> details) {

    public ErrorResponseDTO(String errorMessage, int errorCode) {

        this(errorMessage, errorCode, null);
    }

}
