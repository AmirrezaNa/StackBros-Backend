package com.StackBros.StackBros_Backend.dto;

import com.StackBros.StackBros_Backend.model.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderDTO(
        @NotNull OrderType orderType,

        @NotBlank String customerName,
        @NotBlank String customerPhone,
        @NotBlank @Email String customerEmail,

        // Required only for DELIVERY — validated in the service, not here
        String deliveryStreet,
        String deliveryPostalCode,
        String deliveryCity,

        @NotNull @Future LocalDateTime requestedTime,

        @NotEmpty @Valid List<OrderItemDTO> items
) {}
