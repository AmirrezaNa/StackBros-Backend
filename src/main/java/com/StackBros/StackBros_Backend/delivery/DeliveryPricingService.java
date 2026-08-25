package com.StackBros.StackBros_Backend.delivery;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DeliveryPricingService {

    private static final BigDecimal TIER_1_MAX_KM = BigDecimal.valueOf(1.5);
    private static final BigDecimal TIER_2_MAX_KM = BigDecimal.valueOf(3.0);
    private static final BigDecimal TIER_3_MAX_KM = BigDecimal.valueOf(5.0);

    private static final BigDecimal TIER_1_FEE = BigDecimal.valueOf(1.20);
    private static final BigDecimal TIER_2_FEE = BigDecimal.valueOf(2.20);
    private static final BigDecimal TIER_3_FEE = BigDecimal.valueOf(3.00);

    /**
     * Returns the delivery fee for a given distance, or empty if
     * the address is beyond the 5km delivery range.
     */
    public Optional<BigDecimal> feeForDistance(double distanceKm) {
        BigDecimal distance = BigDecimal.valueOf(distanceKm);

        if (distance.compareTo(TIER_1_MAX_KM) <= 0) {
            return Optional.of(TIER_1_FEE);
        } else if (distance.compareTo(TIER_2_MAX_KM) <= 0) {
            return Optional.of(TIER_2_FEE);
        } else if (distance.compareTo(TIER_3_MAX_KM) <= 0) {
            return Optional.of(TIER_3_FEE);
        } else {
            return Optional.empty(); // out of delivery range
        }
    }
}
