package com.transactions.domain.transaction.aggregate;

import com.transactions.domain.transaction.validators.TransactionValidator;
import com.transactions.domain.transaction.value_objects.TransactionStatus;
import com.transactions.domain.wallet.aggregate.WalletAggregate;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TransactionAggregate {
    public UUID id;
    public WalletAggregate senderWallet;
    public WalletAggregate receiverWallet;
    public Double amount;
    public TransactionStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    @PostConstruct
    public void validateTransaction() {
        TransactionValidator transactionValidator = new TransactionValidator();
        transactionValidator.validate(this);
    }

    public void processTransaction(WalletAggregate senderWallet, WalletAggregate receiverWallet) {
        senderWallet.debit(amount);
        receiverWallet.credit(amount);
        this.status = TransactionStatus.SUCCEEDED;
        this.updatedAt = LocalDateTime.now();
    }

    public void registerFailedTransaction() {
        this.status = TransactionStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public static TransactionAggregate initiateTransaction(
            WalletAggregate senderWallet,
            WalletAggregate receiverWallet,
            Double amount
    ) {
        return new TransactionAggregate(
            UUID.randomUUID(),
            senderWallet,
            receiverWallet,
            amount,
            TransactionStatus.PROCESSING,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }
}
