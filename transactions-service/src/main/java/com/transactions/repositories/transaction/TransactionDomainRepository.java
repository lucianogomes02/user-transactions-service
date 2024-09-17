package com.transactions.repositories.transaction;

import com.transactions.domain.transactions.aggregate.WalletsTransaction;
import com.transactions.domain.transactions.entities.Transaction;
import com.transactions.domain.transactions.entities.Wallet;
import com.transactions.domain.transactions.value_objects.transactions.TransactionStatus;
import com.transactions.repositories.orm.TransactionTable;
import com.transactions.repositories.wallet.WalletDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class TransactionDomainRepository {

    @Autowired
    private TransactionJpaRepository transactionJpaRepository;

    @Autowired
    private WalletDomainRepository walletDomainRepository;

    public List<Transaction> findAll() {
        List<TransactionTable> transactions = transactionJpaRepository.findAll();
        return transactions.stream().map(transaction -> new Transaction(
            transaction.getId(),
            transaction.getSenderId(),
            transaction.getReceiverId(),
            transaction.getAmount(),
            transaction.getStatus(),
            transaction.getCreatedAt(),
            transaction.getUpdatedAt()
        )).toList();
    }

    public WalletsTransaction findWalletTransactionById(UUID id) {
        Transaction transaction = findById(id);
        if (transaction == null) {
            return null;
        }

        Wallet senderWallet = walletDomainRepository.findByUserId(transaction.senderId);
        Wallet receiverWallet = walletDomainRepository.findByUserId(transaction.receiverId);

        if (senderWallet == null || receiverWallet == null) {
            return null;
        }

        return new WalletsTransaction(
            transaction,
            senderWallet,
            receiverWallet,
            transaction.amount
        );
    }

    private Transaction findById(UUID id) {
        TransactionTable transaction = transactionJpaRepository.findById(id).orElse(null);
        if (transaction == null) {
            return null;
        }
        return new Transaction(
            transaction.getId(),
            transaction.getSenderId(),
            transaction.getReceiverId(),
            transaction.getAmount(),
            transaction.getStatus(),
            transaction.getCreatedAt(),
            transaction.getUpdatedAt()
        );
    }

    public void save(WalletsTransaction walletsTransaction) {
        TransactionTable transactionTable = new TransactionTable(
            walletsTransaction.transaction.id,
            walletsTransaction.transaction.senderId,
            walletsTransaction.transaction.receiverId,
            walletsTransaction.transaction.amount,
            walletsTransaction.transaction.status,
            walletsTransaction.transaction.createdAt,
            walletsTransaction.transaction.updatedAt
        );
        transactionJpaRepository.save(transactionTable);
    }

    public void updateTransactionStatus(UUID transactionId, TransactionStatus status) {
        TransactionTable transactionTable = transactionJpaRepository.findById(transactionId).orElse(null);
        if (transactionTable == null) {
            return;
        }
        transactionTable.setStatus(status);
        transactionTable.setUpdatedAt(LocalDateTime.now());
        transactionJpaRepository.save(transactionTable);
    }
}
