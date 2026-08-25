package com.StackBros.StackBros_Backend.delivery;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DeliveryQuoteService {

    private final GeocodingService geocodingService;
    private final DeliveryPricingService pricingService;

    @Value("${restaurant.address.street}")
    private String restaurantStreet;
    @Value("${restaurant.address.postal-code}")
    private String restaurantPostalCode;
    @Value("${restaurant.address.city}")
    private String restaurantCity;
    @Value("${restaurant.address.country}")
    private String restaurantCountry;

    private GeoPoint restaurantLocation;

    public DeliveryQuoteService(GeocodingService geocodingService, DeliveryPricingService pricingService) {
        this.geocodingService = geocodingService;
        this.pricingService = pricingService;
    }

    @PostConstruct
    public void init() {
        restaurantLocation = geocodingService
                .geocode(restaurantStreet, restaurantPostalCode, restaurantCity, restaurantCountry)
                .orElseThrow(() -> new IllegalStateException(
                        "Could not geocode restaurant address on startup — check restaurant.address.* properties"));
    }

    public DeliveryQuoteResult quote(String street, String postalCode, String city) {
        Optional<GeoPoint> customerLocation = geocodingService.geocode(street, postalCode, city, "Germany");

        if (customerLocation.isEmpty()) {
            return DeliveryQuoteResult.undeliverable("Could not find that address");
        }

        double distanceKm = DistanceCalculator.distanceKm(restaurantLocation, customerLocation.get());
        Optional<BigDecimal> fee = pricingService.feeForDistance(distanceKm);

        if (fee.isEmpty()) {
            return DeliveryQuoteResult.undeliverable("Address is outside our 5km delivery range");
        }

        return DeliveryQuoteResult.deliverable(BigDecimal.valueOf(distanceKm), fee.get());
    }
}