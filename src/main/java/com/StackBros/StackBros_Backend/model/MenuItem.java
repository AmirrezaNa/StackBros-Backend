package com.StackBros.StackBros_Backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String nameDe;
    @Column(nullable = false)
    private String nameEn;

    @Column(columnDefinition = "TEXT")
    private String descDe;
    @Column(columnDefinition = "TEXT")
    private String descEn;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal price;

    private String imageUrl;

    @Column(nullable = false)
    private boolean isNew = false;

    @Column(nullable = false)
    private boolean available = true;

    @Column(nullable = false)
    private Integer sortOrder = 0;

    public MenuItem() {

    }

    public MenuItem(Category category, String nameDe, String nameEn, int sortOrder) {
        this.category = category;
        this.nameDe = nameDe;
        this.nameEn = nameEn;
        this.sortOrder = sortOrder;
    }
}