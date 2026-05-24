package com.practice.api.dto;

import java.time.LocalDateTime;

public record PostResponse(String title,
                           String content,
                           LocalDateTime createdAt,
                           boolean published,
                           Long userId,
                           String user) {
}
