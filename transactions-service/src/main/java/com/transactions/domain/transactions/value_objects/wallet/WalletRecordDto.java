package com.transactions.domain.transactions.value_objects.wallet;

import jakarta.validation.constraints.NotBlank;

public record WalletRecordDto(
    @NotBlank
    String balance
) {
}
