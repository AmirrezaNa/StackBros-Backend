package com.StackBros.StackBros_Backend.controller;

import com.StackBros.StackBros_Backend.delivery.DeliveryQuoteResult;
import com.StackBros.StackBros_Backend.delivery.DeliveryQuoteService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeliveryController {

    private final DeliveryQuoteService deliveryQuoteService;

    public DeliveryController(DeliveryQuoteService deliveryQuoteService) {
        this.deliveryQuoteService = deliveryQuoteService;
    }

    public record DeliveryQuoteRequest(
            @NotBlank String street,
            @NotBlank String postalCode,
            @NotBlank String city
    ) {}

    @PostMapping("/api/delivery/quote")
    public DeliveryQuoteResult quote(@RequestBody DeliveryQuoteRequest request) {
        return deliveryQuoteService.quote(request.street(), request.postalCode(), request.city());
    }
}
