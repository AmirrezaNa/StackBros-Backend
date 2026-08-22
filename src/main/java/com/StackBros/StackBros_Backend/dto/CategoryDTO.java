package com.StackBros.StackBros_Backend.dto;

import com.StackBros.StackBros_Backend.model.Category;
import com.StackBros.StackBros_Backend.model.MenuItem;

import java.util.Comparator;
import java.util.List;

public record CategoryDTO(
        String key,
        LocalizedText eyebrow,
        LocalizedText title,
        LocalizedText note,
        List<MenuItemDTO> items
){
    public static CategoryDTO MenuCategoryEntityToDTO(Category category) {
        List<MenuItemDTO> items = category.getItems().stream()
                .filter(MenuItem::isAvailable)
                .sorted(Comparator.comparing(MenuItem::getSortOrder))
                .map(item -> new MenuItemDTO(
                        item.getId(),
                        new LocalizedText(item.getNameDe(), item.getNameEn()),
                        new LocalizedText(item.getDescDe(), item.getDescEn()),
                        item.getPrice(),
                        item.getImageUrl(),
                        item.isNew()
                ))
                .toList();

        return new CategoryDTO(
                category.getKey(),
                new LocalizedText(category.getEyebrowDe(), category.getEyebrowEn()),
                new LocalizedText(category.getTitleDe(), category.getTitleEn()),
                new LocalizedText(category.getNoteDe(), category.getNoteEn()),
                items
        );
    }
}
