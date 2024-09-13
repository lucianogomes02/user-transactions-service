package com.transactions.domain.transaction.value_objects;

public record TransactionPublicDto(
    String id,
    String senderId,
    String receiverId,
    String amount,
    String status,
    String createdAt
) {
}
