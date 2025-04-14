package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.Category;

/**
 * Репозиторий для работы с категориями.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    /**
     * Проверяет, существует ли категория с указанным именем (игнорируя регистр).
     *
     * @param name имя категории для проверки
     * @return true, если категория с таким именем существует, иначе false
     */
    Boolean existsByNameIgnoreCase(String name);
}

