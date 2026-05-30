package com.practice.api.dto.security;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank String username, String password) {
}
