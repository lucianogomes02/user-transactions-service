package com.transactions.domain.wallet.value_objects;

public record WalletPublicDto(
    String id,
    String userId,
    String balance,
    String createdAt
) {
}
