package com.transactions.domain.transactions.aggregate;

import com.transactions.domain.transactions.entities.Transaction;
import com.transactions.domain.transactions.entities.Wallet;
import com.transactions.domain.transactions.validators.transactions.TransactionValidator;
import com.transactions.domain.transactions.validators.wallet.WalletValidator;
import com.transactions.domain.transactions.value_objects.transactions.TransactionStatus;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
public class WalletsTransaction {
    public Transaction transaction;
    public Wallet senderWallet;
    public Wallet receiverWallet;
    public Double amount;

    private WalletValidator walletValidator;
    private TransactionValidator transactionValidator;

    public WalletsTransaction(
        Transaction transaction,
        Wallet senderWallet,
        Wallet receiverWallet,
        Double amount
    ) {
        this.transaction = transaction;
        this.senderWallet = senderWallet;
        this.receiverWallet = receiverWallet;
        this.amount = amount;
    }

    @PostConstruct
    public void initValidators() {
        this.transactionValidator = new TransactionValidator();
        this.walletValidator = new WalletValidator();
    }

    public void processTransaction() {
        this.processTransactionBetweenWallets();
        this.transaction.status = TransactionStatus.SUCCEEDED;
        this.transaction.updatedAt = LocalDateTime.now();
    }

    public void registerFailedTransaction() {
        this.transaction.status = TransactionStatus.FAILED;
        this.transaction.updatedAt = LocalDateTime.now();
    }

    public Transaction initiateTransaction(
        String senderId,
        String receiverId,
        Double amount
    ) {
        Transaction transaction = new Transaction(
            UUID.randomUUID(),
            senderId,
            receiverId,
            amount,
            TransactionStatus.PROCESSING,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        this.transactionValidator.validate(transaction);
        return transaction;
    }

    public static Wallet registerWallet(String userId, Double initialBalance) {
        Wallet wallet = new Wallet();
        wallet.id = UUID.randomUUID();
        wallet.userId = userId;
        wallet.balance = initialBalance;
        wallet.createdAt = LocalDateTime.now();
        wallet.updatedAt = LocalDateTime.now();
        return wallet;
    }

    private void processTransactionBetweenWallets() {
        this.walletValidator.validate(senderWallet, receiverWallet, amount);
        this.debitFromSendersWallet(amount);
        this.creditInReceiversWallet(amount);
    }

    private void debitFromSendersWallet(Double amount) {
        senderWallet.balance -= amount;
        senderWallet.updatedAt = LocalDateTime.now();
    }

    private void creditInReceiversWallet(Double amount) {
        receiverWallet.balance += amount;
        receiverWallet.updatedAt = LocalDateTime.now();
    }
}
