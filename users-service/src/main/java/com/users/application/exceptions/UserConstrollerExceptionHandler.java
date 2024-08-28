package com.users.application.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    private record ValidationErrorData(String campo, String mensagem) {
        public ValidationErrorData(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
