package com.transactions.controllers;

import com.transactions.domain.value_objects.TransactionPublicDto;
import com.transactions.domain.value_objects.TransactionRecordDto;
import com.transactions.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<Iterable<TransactionPublicDto>> getTransactions() {
        Iterable<TransactionPublicDto> transactions = transactionService.getTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionPublicDto> createTransaction(@RequestBody @Valid TransactionRecordDto transactionRecordDto) {
        TransactionPublicDto transaction = transactionService.createTransaction(transactionRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
