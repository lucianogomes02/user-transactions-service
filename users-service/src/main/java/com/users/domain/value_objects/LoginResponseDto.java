package com.users.domain.value_objects;

public record LoginResponseDto(
        String accessToken,
        Long expiresIn
) {
}
