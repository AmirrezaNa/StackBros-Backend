package com.StackBros.StackBros_Backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemDTO(
        @NotNull Long menuItemId,
        @Min(1) int quantity
) {}
