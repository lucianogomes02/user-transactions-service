package com.transactions.domain.aggregate.transaction;

import com.transactions.domain.value_objects.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Transaction")
@Table(name = "TB_TRANSACTIONS")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sender_id", nullable = false)
    private UUID senderId;

    @Column(name = "receiver_id", nullable = false)
    private UUID receiverId;

    @Column(name="amount", nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private TransactionStatus status = TransactionStatus.PROCESSING;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
