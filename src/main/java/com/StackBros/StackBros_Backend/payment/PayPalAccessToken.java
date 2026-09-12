package com.StackBros.StackBros_Backend.payment;

import java.time.Instant;

public record PayPalAccessToken(String token, Instant expiresAt) {
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt.minusSeconds(30)); // refresh a bit early
    }
}