package com.StackBros.StackBros_Backend.dto;

import com.StackBros.StackBros_Backend.model.OrderStatus;
import com.StackBros.StackBros_Backend.model.PaymentStatus;

import java.math.BigDecimal;

public record OrderDTO(
        Long id,
        OrderStatus status,
        PaymentStatus paymentStatus,
        BigDecimal totalPrice
) {}
