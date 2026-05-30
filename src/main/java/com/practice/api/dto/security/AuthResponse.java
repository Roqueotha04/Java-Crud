package com.practice.api.dto.security;

public record AuthResponse(String username,
                           String message,
                           String jwtToken,
                           Boolean status) {
}
