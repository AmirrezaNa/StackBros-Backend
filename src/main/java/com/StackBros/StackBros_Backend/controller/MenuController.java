package com.StackBros.StackBros_Backend.controller;


import com.StackBros.StackBros_Backend.dto.CategoryDTO;
import com.StackBros.StackBros_Backend.repository.MenuCategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    private final MenuCategoryRepository categoryRepository;

    public MenuController(MenuCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/menu")
    public List<CategoryDTO> getMenu() {
        return categoryRepository.findAllByOrderBySortOrderAsc()
                .stream()
                .map(CategoryDTO::MenuCategoryEntityToDTO)
                .toList();
    }


}