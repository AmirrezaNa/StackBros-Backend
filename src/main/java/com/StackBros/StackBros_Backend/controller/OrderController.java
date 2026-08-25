package com.StackBros.StackBros_Backend.controller;

import com.StackBros.StackBros_Backend.delivery.DeliveryQuoteResult;
import com.StackBros.StackBros_Backend.delivery.DeliveryQuoteService;
import com.StackBros.StackBros_Backend.dto.CreateOrderDTO;
import com.StackBros.StackBros_Backend.dto.OrderItemDTO;
import com.StackBros.StackBros_Backend.dto.OrderDTO;
import com.StackBros.StackBros_Backend.model.*;
import com.StackBros.StackBros_Backend.repository.MenuItemRepository;
import com.StackBros.StackBros_Backend.repository.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final DeliveryQuoteService deliveryQuoteService;

    public OrderController(OrderRepository orderRepository, MenuItemRepository menuItemRepository, DeliveryQuoteService deliveryQuoteService) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.deliveryQuoteService = deliveryQuoteService;
    }

    @PostMapping("/api/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@Valid @RequestBody CreateOrderDTO request) {

        if (request.orderType() == OrderType.DELIVERY) {
            if (request.deliveryStreet() == null || request.deliveryStreet().isBlank()
                    || request.deliveryPostalCode() == null || request.deliveryPostalCode().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Delivery address is required for delivery orders");
            }
        }

        Order order = new Order(
                request.orderType(),
                request.customerName(),
                request.customerPhone(),
                request.customerEmail(),
                request.requestedTime()
        );

        if (request.orderType() == OrderType.DELIVERY) {
            order.setDeliveryStreet(request.deliveryStreet());
            order.setDeliveryPostalCode(request.deliveryPostalCode());
            order.setDeliveryCity(request.deliveryCity());

            DeliveryQuoteResult quote = deliveryQuoteService.quote(
                    request.deliveryStreet(), request.deliveryPostalCode(), request.deliveryCity());

            if (!quote.deliverable()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, quote.message());
            }

            order.setDeliveryDistanceKm(quote.distanceKm());
            order.setDeliveryFee(quote.fee());
        }

        BigDecimal itemsTotal = BigDecimal.ZERO;

        for (OrderItemDTO itemRequest : request.items()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.menuItemId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Menu item not found: " + itemRequest.menuItemId()));

            if (!menuItem.isAvailable()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Menu item is not available: " + menuItem.getNameEn());
            }

            OrderItem orderItem = new OrderItem(menuItem, itemRequest.quantity(), menuItem.getPrice());
            order.addOrderItem(orderItem);

            itemsTotal = itemsTotal.add(orderItem.getSubtotal());
        }

        BigDecimal deliveryFee = order.getDeliveryFee() != null ? order.getDeliveryFee() : BigDecimal.ZERO;
        order.setTotalPrice(itemsTotal.add(deliveryFee));

        Order saved = orderRepository.save(order);

        return new OrderDTO(saved.getId(), saved.getStatus(), saved.getPaymentStatus(), saved.getTotalPrice());
    }
}
