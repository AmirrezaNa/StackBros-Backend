package com.StackBros.StackBros_Backend.controller;

import com.StackBros.StackBros_Backend.model.Order;
import com.StackBros.StackBros_Backend.model.PaymentStatus;
import com.StackBros.StackBros_Backend.payment.PayPalService;
import com.StackBros.StackBros_Backend.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/orders/{orderId}/pay")
public class PaymentController {

    private final OrderRepository orderRepository;
    private final PayPalService payPalService;

    public PaymentController(OrderRepository orderRepository, PayPalService payPalService) {
        this.orderRepository = orderRepository;
        this.payPalService = payPalService;
    }

    @PostMapping("/create")
    public Map<String, String> createPayment(@PathVariable Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is not awaiting payment");
        }

        PayPalService.PayPalOrderCreation creation =
                payPalService.createOrder(order.getId(), order.getTotalPrice(), "EUR");

        order.setPaypalOrderId(creation.paypalOrderId());
        orderRepository.save(order);

        return Map.of(
                "paypalOrderId", creation.paypalOrderId(),
                "approveUrl", creation.approveUrl()
        );
    }

    @PostMapping("/capture")
    public Map<String, String> capturePayment(@PathVariable Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getPaypalOrderId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No PayPal order was created for this order");
        }

        boolean success = payPalService.captureOrder(order.getPaypalOrderId());

        if (!success) {
            order.setPaymentStatus(PaymentStatus.FAILED);
            orderRepository.save(order);
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "Payment capture failed");
        }

        order.setPaymentStatus(PaymentStatus.PAID);
        orderRepository.save(order);

        return Map.of("status", "PAID");
    }
}