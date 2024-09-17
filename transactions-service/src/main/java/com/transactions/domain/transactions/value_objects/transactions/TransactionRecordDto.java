package com.transactions.domain.transactions.value_objects.transactions;

import jakarta.validation.constraints.NotBlank;

public record TransactionRecordDto(
        @NotBlank
        String receiverId,
        @NotBlank
        String amount
) {
}
