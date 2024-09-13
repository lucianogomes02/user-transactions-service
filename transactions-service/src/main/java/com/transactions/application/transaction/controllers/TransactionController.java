package com.transactions.application.transaction.controllers;

import com.transactions.domain.transaction.value_objects.TransactionPublicDto;
import com.transactions.domain.transaction.value_objects.TransactionRecordDto;
import com.transactions.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Transaction Controller", description = "Endpoints for transactions between users")
@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Get all transactions")
    @GetMapping("/transactions")
    public ResponseEntity<Iterable<TransactionPublicDto>> getTransactions(JwtAuthenticationToken jwt) {
        Iterable<TransactionPublicDto> transactions = transactionService.getTransactions();
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Create a transaction")
    @PostMapping("/transactions")
    public ResponseEntity<TransactionPublicDto> createTransaction(
            @RequestBody @Valid TransactionRecordDto transactionRecordDto,
            JwtAuthenticationToken jwt
    ) {
        String currentUserId = jwt.getToken().getClaimAsString("sub");
        TransactionPublicDto transaction = transactionService.createTransaction(transactionRecordDto, currentUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
