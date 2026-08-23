package com.StackBros.StackBros_Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_categories")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String categoryKey;

    @Column(nullable = false)
    private String eyebrowDe;
    @Column(nullable = false)
    private String eyebrowEn;

    @Column(nullable = false)
    private String titleDe;
    @Column(nullable = false)
    private String titleEn;

    @Column(columnDefinition = "TEXT")
    private String noteDe;
    @Column(columnDefinition = "TEXT")
    private String noteEn;

    @Column(nullable = false)
    private Integer sortOrder = 0;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<MenuItem> menuItems = new ArrayList<>();


    public Category() {

    }

    public Category(String categoryKey, String eyebrowDe, String eyebrowEn, String titleDe, String titleEn, Integer sortOrder) {
        this.categoryKey = categoryKey;
        this.eyebrowDe = eyebrowDe;
        this.eyebrowEn = eyebrowEn;
        this.titleDe = titleDe;
        this.titleEn = titleEn;
        this.sortOrder = sortOrder;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        menuItem.setCategory(this);
    }
}