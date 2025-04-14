package ru.practicum.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryService;

/**
 * Класс AdminCategoryController контроллер для управления категориями в административной панели.
 */
@RestController
@RequestMapping("admin/categories")
@Slf4j
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    /**
     * Метод createCategory создаёт новую категорию.
     *
     * @param newCategoryDto объект, содержащий данные для создания категории
     * @return созданный объект CategoryDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Получен запрос на создание категории.");
        return categoryService.createCategory(newCategoryDto);
    }

    /**
     * Метод deleteCategory удаляет категорию.
     *
     * @param catId идентификатор, удаляемой категории
     */
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Integer catId) {
        log.info("Получен запрос на удаление категории.");
        categoryService.deleteCategory(catId);
    }

    /**
     * Метод updateCategory обновляет существующую категорию.
     *
     * @param categoryDto объект, содержащий данные для создания категории
     * @param catId       идентификатор, обновляемой категории
     * @return обновленный объект CategoryDto
     */
    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto,
                                      @PathVariable Integer catId) {
        log.info("Получен запрос на обновление категории.");
        return categoryService.updateCategory(categoryDto, catId);
    }
}