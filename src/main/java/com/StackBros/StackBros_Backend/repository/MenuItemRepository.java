package com.StackBros.StackBros_Backend.repository;

import com.StackBros.StackBros_Backend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
