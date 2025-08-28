package com.example.git.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GitException.class)
    public ResponseEntity<ErrorResponse> handleGitException(GitException ex) {
        ErrorResponse error = new ErrorResponse("GIT_ERROR",         // custom error code
                ex.getMessage(), ex.getCause() != null ? ex.getCause().toString() : null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse("GENERIC_ERROR", "Unexpected error occurred", ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
