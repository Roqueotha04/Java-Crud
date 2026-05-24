package com.practice.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PostRequest(@NotBlank String title,
                          @NotBlank @Size(min = 10, max = 150)String content,
                          @PastOrPresent LocalDateTime createdAt,
                          boolean published,
                          @NotNull Long userId) {
}
