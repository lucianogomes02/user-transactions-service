package com.users.domain.value_objects;

public record UserPublicDto(
        String id,
        String name,
        String email,
        String cpf,
        String createdAt,
        String updatedAt
) {
}
