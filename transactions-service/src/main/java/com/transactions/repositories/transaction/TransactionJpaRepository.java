package com.transactions.repositories.transaction;

import com.transactions.repositories.orm.TransactionTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionTable, UUID> {
}
