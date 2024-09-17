package com.transactions.repositories.wallet;

import com.transactions.repositories.orm.WalletTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletJpaRepository extends JpaRepository<WalletTable, UUID> {
    WalletTable findByUserId(String userId);
}
