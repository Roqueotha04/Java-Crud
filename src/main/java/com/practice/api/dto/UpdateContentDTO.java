package com.practice.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateContentDTO(@NotBlank @Size(min = 10, max = 150) String content) {
}
