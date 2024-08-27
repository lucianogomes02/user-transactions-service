package com.transactions.domain.aggregate;

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
    private UUID senderId;
    private UUID receiverId;
    private Double amount;
    private LocalDateTime createdAt = LocalDateTime.now();
}
