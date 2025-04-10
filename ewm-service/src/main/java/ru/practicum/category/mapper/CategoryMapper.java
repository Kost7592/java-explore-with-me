package ru.practicum.category.mapper;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

/**
 * Класс для преобразования объектов Category в DTO-объекты CategoryDto.
 */
public class CategoryMapper {
    /**
     * Преобразует объект Category в DTO-объект CategoryDto.
     *
     * @param category объект категории для преобразования
     * @return преобразованный объект CategoryDto
     */
    public static CategoryDto categoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
