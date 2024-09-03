package com.transactions.application.exceptions.transaction;

import com.transactions.application.exceptions.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class TransactionControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto("Transação não encontrada", "404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var erros = e.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(ValidationErrorData::new).toList());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ExceptionDto exceptionDto = new ExceptionDto("Transação inválida ou já cadastrada", "400");
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ExceptionHandler(TransactionValidationException.class)
    public ResponseEntity handleTransactionValidationException(TransactionValidationException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage(), "400");
        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ExceptionDto> handleResourceAccessException(ResourceAccessException e) {
        var exceptionDto = new ExceptionDto("Transações temporariamente indisponíveis", "503");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exceptionDto);
    }

    private record ValidationErrorData(String campo, String mensagem) {
        public ValidationErrorData(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
