package com.transactions.domain.transactions.aggregate;

import com.transactions.domain.transactions.entities.Transaction;
import com.transactions.domain.transactions.entities.Wallet;
import com.transactions.domain.transactions.validators.transactions.TransactionValidator;
import com.transactions.domain.transactions.validators.wallet.WalletValidator;
import com.transactions.domain.transactions.value_objects.transactions.TransactionStatus;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class WalletsTransaction {
    public Transaction transaction;
    public Wallet senderWallet;
    public Wallet receiverWallet;
    public Double amount;

    private final TransactionValidator transactionValidator = new TransactionValidator();
    private final WalletValidator walletValidator = new WalletValidator();

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

    public void processTransaction() {
        processTransactionBetweenWallets();
        transaction.status = TransactionStatus.SUCCEEDED;
        transaction.updatedAt = LocalDateTime.now();
    }

    public void registerFailedTransaction() {
        transaction.status = TransactionStatus.FAILED;
        transaction.updatedAt = LocalDateTime.now();
    }

    public Transaction initiateTransaction(
        String senderId,
        String receiverId,
        Double amount
    ) {
        Transaction transaction = new Transaction();
        transaction.senderId = senderId;
        transaction.receiverId = receiverId;
        transaction.amount = amount;
        transaction.status = TransactionStatus.PROCESSING;
        transaction.createdAt = LocalDateTime.now();
        transaction.updatedAt = LocalDateTime.now();
        transactionValidator.validate(transaction);
        return transaction;
    }

    public static Wallet registerWallet(String userId, Double initialBalance) {
        Wallet wallet = new Wallet();
        wallet.userId = userId;
        wallet.balance = initialBalance;
        wallet.createdAt = LocalDateTime.now();
        wallet.updatedAt = LocalDateTime.now();
        return wallet;
    }

    private void processTransactionBetweenWallets() {
        walletValidator.validate(senderWallet, receiverWallet, amount);
        debitFromSendersWallet(amount);
        creditInReceiversWallet(amount);
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
