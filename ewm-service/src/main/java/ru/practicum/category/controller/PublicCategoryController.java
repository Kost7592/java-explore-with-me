package ru.practicum.category.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import java.util.List;

/**
 * Класс PublicCategoryController контроллер для управления категориями.
 */
@RestController
@RequestMapping("/categories")
@Slf4j
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService categoryService;

    /**
     * Метод getCategories возвращает список категорий.
     *
     * @param from индекс, с которого начинается выборка категорий (по умолчанию 0)
     * @param size количество категорий, которые нужно вернуть (по умолчанию 10)
     * @return список объектов CategoryDto
     */
    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Получен запрос на получение списка категорий.");
        return categoryService.getCategories(from, size);
    }

    /**
     * Метод getCategoryById возвращает категорию по её идентификатору.
     *
     * @param catId идентификатор категории
     * @return объект CategoryDto, соответствующий указанной категории
     */
    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Integer catId) {
        log.info("Получен запрос на получение категории по id.");
        return categoryService.getCategoryById(catId);
    }
}
