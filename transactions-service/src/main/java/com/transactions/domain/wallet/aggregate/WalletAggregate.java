package com.transactions.domain.wallet.aggregate;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class WalletAggregate {
    private UUID id;
    private String userId;
    private Double balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void debit(Double amount) {
        this.balance -= amount;
        this.updatedAt = LocalDateTime.now();
    }

    public void credit(Double amount) {
        this.balance += amount;
        this.updatedAt = LocalDateTime.now();
    }

    public static WalletAggregate createWallet(String userId, Double initialBalance) {
        WalletAggregate wallet = new WalletAggregate();
        wallet.id = UUID.randomUUID();
        wallet.userId = userId;
        wallet.balance = initialBalance;
        wallet.createdAt = LocalDateTime.now();
        wallet.updatedAt = LocalDateTime.now();
        return wallet;
    }
}
