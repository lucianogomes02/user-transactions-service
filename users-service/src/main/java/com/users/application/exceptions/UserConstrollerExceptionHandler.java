package com.users.application.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class UserConstrollerExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto("Usuário não encontrado", "404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var erros = e.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(ValidationErrorData::new).toList());
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity handleUserValidationException(UserValidationException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage(), "400");
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ExceptionDto> handleResourceAccessException(ResourceAccessException e) {
        var exceptionDto = new ExceptionDto("Usuários temporariamente indisponíveis", "503");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exceptionDto);
    }

    private record ValidationErrorData(String campo, String mensagem) {
        public ValidationErrorData(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
