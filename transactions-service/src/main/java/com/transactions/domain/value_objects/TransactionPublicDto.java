package com.transactions.domain.value_objects;

public record TransactionPublicDto(
    String id,
    String senderId,
    String receiverId,
    String amount,
    String createdAt
) {
}
