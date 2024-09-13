package com.transactions.domain.wallet.value_objects;

import jakarta.validation.constraints.NotBlank;

public record WalletRecordDto(
    @NotBlank
    String balance
) {
}
