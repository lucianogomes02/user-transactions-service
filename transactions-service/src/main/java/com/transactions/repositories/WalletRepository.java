package com.transactions.repositories;

import com.transactions.domain.aggregate.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Wallet findByUserId(String userId);
}
