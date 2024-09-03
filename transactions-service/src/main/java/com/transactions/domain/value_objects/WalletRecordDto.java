package com.transactions.domain.value_objects;

import jakarta.validation.constraints.NotBlank;

public record WalletRecordDto(
    @NotBlank
    String balance
) {
}
