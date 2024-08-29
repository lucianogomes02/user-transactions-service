package com.auth.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;


@RestControllerAdvice
public class AuthControllerExceptionHandler {
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ExceptionDto> handleResourceAccessException(ResourceAccessException e) {
        var exceptionDto = new ExceptionDto("Login temporariamente indisponível", "503");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exceptionDto);
    }
}
