package com.StackBros.StackBros_Backend.dto;

import java.math.BigDecimal;

public record MenuItemDTO(
        Long id,
        LocalizedText name,
        LocalizedText desc,
        BigDecimal price,
        String image,
        boolean isNew
) {
}
