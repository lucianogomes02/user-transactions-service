package com.users.domain.value_objects;

import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(
        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String cpf
) {
}
