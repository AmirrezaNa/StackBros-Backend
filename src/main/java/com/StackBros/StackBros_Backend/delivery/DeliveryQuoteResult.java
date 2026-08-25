package com.StackBros.StackBros_Backend.delivery;

import java.math.BigDecimal;

public record DeliveryQuoteResult(
        boolean deliverable,
        BigDecimal distanceKm,
        BigDecimal fee,
        String message
) {
    public static DeliveryQuoteResult deliverable(BigDecimal distanceKm, BigDecimal fee) {
        return new DeliveryQuoteResult(true, distanceKm, fee, null);
    }

    public static DeliveryQuoteResult undeliverable(String message) {
        return new DeliveryQuoteResult(false, null, null, message);
    }
}
