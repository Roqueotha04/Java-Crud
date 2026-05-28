package com.practice.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserEntityRequest(
        @Size(min = 3, max = 50) String name,
        @NotBlank String lastName,
        @NotBlank String username,
        @NotBlank @Size(min = 6) String password
) {
}
