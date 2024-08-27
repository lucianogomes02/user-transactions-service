package com.users.domain.value_objects;

import jakarta.validation.constraints.NotBlank;

public record UserTransactionDto(
        @NotBlank
        String senderId,

        @NotBlank
        String receiverId,

        @NotBlank
        Double amount
) {
}
