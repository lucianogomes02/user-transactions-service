package com.auth.dto;

public record LoginResponse(
        String accessToken,
        Long expiresIn
) {
}
