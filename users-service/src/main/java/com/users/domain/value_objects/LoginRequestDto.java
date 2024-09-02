package com.users.domain.value_objects;

public record LoginRequestDto(
        String username,
        String password
) {
}
