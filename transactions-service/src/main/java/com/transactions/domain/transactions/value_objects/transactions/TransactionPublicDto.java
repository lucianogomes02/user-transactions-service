package com.transactions.domain.transactions.value_objects.transactions;

public record TransactionPublicDto(
    String id,
    String senderId,
    String receiverId,
    String amount,
    String status,
    String createdAt
) {
}
