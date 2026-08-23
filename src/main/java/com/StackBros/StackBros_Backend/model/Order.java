package com.StackBros.StackBros_Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;


    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String customerPhone;
    @Column(nullable = false)
    private String customerEmail;


    private String deliveryStreet;
    private String deliveryPostalCode;
    private String deliveryCity;
    private BigDecimal deliveryDistanceKm;
    private BigDecimal deliveryFee;


    @Column(nullable = false)
    private LocalDateTime requestedTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.RECEIVED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private String paypalOrderId;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(OrderType orderType, String customerName, String customerPhone,
                 String customerEmail, LocalDateTime requestedTime) {
        this.orderType = orderType;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.requestedTime = requestedTime;
    }

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }
}
