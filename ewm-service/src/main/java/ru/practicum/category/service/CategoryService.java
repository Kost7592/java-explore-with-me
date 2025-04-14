package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

/**
 * Сервисный интерфейс для работы с категориями.
 */
public interface CategoryService {
    /**
     * Создаёт новую категорию.
     *
     * @param newCategoryDto объект, содержащий данные для создания категории
     * @return созданный объект CategoryDto
     */
    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    /**
     * Обновляет категорию.
     *
     * @param categoryDto объект, содержащий новые данные для категории
     * @param catId       идентификатор категории, которую нужно обновить
     * @return обновлённый объект CategoryDto
     */
    CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);

    /**
     * Удаляет категорию по её идентификатору.
     *
     * @param catId идентификатор категории для удаления
     */
    void deleteCategory(Integer catId);

    /**
     * Возвращает категорию по её идентификатору.
     *
     * @param catId идентификатор категории
     * @return объект CategoryDto, соответствующий указанной категории
     */
    CategoryDto getCategoryById(Integer catId);

    /**
     * Возвращает список категорий.
     *
     * @param from индекс, с которого начинается выборка категорий
     * @param size количество категорий, которые нужно вернуть
     * @return список объектов CategoryDto
     */
    List<CategoryDto> getCategories(Integer from, Integer size);
}