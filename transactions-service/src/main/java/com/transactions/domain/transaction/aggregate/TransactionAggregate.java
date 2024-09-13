package com.transactions.domain.transaction.aggregate;

import com.transactions.domain.transaction.value_objects.TransactionStatus;
import com.transactions.domain.wallet.aggregate.WalletAggregate;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TransactionAggregate {
    private UUID id;
    private WalletAggregate senderWallet;
    private WalletAggregate receiverWallet;
    private Double amount;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
        TransactionAggregate transaction = new TransactionAggregate();
        transaction.id = UUID.randomUUID();
        transaction.senderWallet = senderWallet;
        transaction.receiverWallet = receiverWallet;
        transaction.amount = amount;
        transaction.status = TransactionStatus.PROCESSING;
        transaction.createdAt = LocalDateTime.now();
        transaction.updatedAt = LocalDateTime.now();
        return transaction;
    }
}
