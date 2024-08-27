package com.transactions.domain.value_objects;

import jakarta.validation.constraints.NotBlank;

public record TransactionRecordDto(
        @NotBlank
        String senderId,
        @NotBlank
        String receiverId,
        @NotBlank
        String amount
) {
}
