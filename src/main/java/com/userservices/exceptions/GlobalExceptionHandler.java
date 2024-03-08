package com.userservices.exceptions;

import com.userservices.payload.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ApiErrorResponse build = ApiErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }
}
