package com.StackBros.StackBros_Backend.repository;

import com.StackBros.StackBros_Backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
