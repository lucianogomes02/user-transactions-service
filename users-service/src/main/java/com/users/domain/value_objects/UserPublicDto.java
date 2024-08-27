package com.users.domain.value_objects;

public record UserPublicDto(
        String id,
        String name,
        String email,
        String cpf,
        String walletFunds,
        String createdAt,
        String updatedAt
) {
}
