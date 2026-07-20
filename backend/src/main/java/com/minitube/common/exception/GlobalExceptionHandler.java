package com.minitube.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // If a service throws DuplicateResourceException, return a clean 409 JSON response to the client.
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException exception) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(exception.getMessage()));
        
    } 

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCResponseEntity(InvalidCredentialsException exception) {

    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(new ErrorResponse(exception.getMessage()));
    }
    
    public record ErrorResponse(String message) {
    }
    
}
