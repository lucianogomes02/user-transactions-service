package com.transactions.repositories.wallet;

import com.transactions.domain.transactions.entities.Wallet;
import com.transactions.repositories.orm.WalletTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class WalletDomainRepository {
    @Autowired
    private WalletJpaRepository walletJpaRepository;

    public Wallet findById(UUID id) {
        WalletTable walletTable = walletJpaRepository.findById(id).orElse(null);
        if (walletTable == null) {
            return null;
        }
        return new Wallet(
            walletTable.getId(),
            walletTable.getUserId(),
            walletTable.getBalance(),
            walletTable.getCreatedAt(),
            walletTable.getUpdatedAt()
        );
    }

    public Wallet findByUserId(String userId) {
        WalletTable walletTable = walletJpaRepository.findByUserId(userId);
        if (walletTable == null) {
            return null;
        }
        return new Wallet(
            walletTable.getId(),
            walletTable.getUserId(),
            walletTable.getBalance(),
            walletTable.getCreatedAt(),
            walletTable.getUpdatedAt()
        );
    }

    public void save(Wallet wallet) {
        WalletTable walletTable = new WalletTable(
            wallet.id,
            wallet.userId,
            wallet.balance,
            wallet.createdAt,
            wallet.updatedAt
        );
        walletJpaRepository.save(walletTable);
    }

    public void updateWalletBalance(UUID walletId, Double balance) {
        WalletTable walletTable = walletJpaRepository.findById(walletId).orElse(null);
        if (walletTable == null) {
            return;
        }
        walletTable.setBalance(balance);
        walletTable.setUpdatedAt(walletTable.getUpdatedAt());
        walletJpaRepository.save(walletTable);
    }
}
