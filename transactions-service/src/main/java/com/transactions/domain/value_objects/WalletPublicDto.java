package com.transactions.domain.value_objects;

public record WalletPublicDto(
    String id,
    String userId,
    String balance,
    String createdAt
) {
}
