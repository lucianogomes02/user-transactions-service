package com.transactions.domain.transactions.entities;

import com.transactions.domain.transactions.value_objects.transactions.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    public UUID id;
    public String senderId;
    public String receiverId;
    public Double amount;
    public TransactionStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
