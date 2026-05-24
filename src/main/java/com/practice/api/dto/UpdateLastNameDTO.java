package com.practice.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateLastNameDTO(@NotBlank String name) {
}
