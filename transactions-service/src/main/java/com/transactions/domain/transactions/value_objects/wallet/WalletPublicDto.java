package com.transactions.domain.transactions.value_objects.wallet;

public record WalletPublicDto(
    String id,
    String userId,
    String balance,
    String createdAt
) {
}
