package com.practice.api.dto.security;

import com.practice.api.entity.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthCreateUser(@NotBlank String username,
                             @NotBlank String password,
                             String name,
                             String lastname,
                             @NotNull RoleEnum roleEnum) {
}
