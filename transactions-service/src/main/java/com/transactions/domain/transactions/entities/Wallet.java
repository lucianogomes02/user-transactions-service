package com.transactions.domain.transactions.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    public UUID id;
    public String userId;
    public Double balance;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
