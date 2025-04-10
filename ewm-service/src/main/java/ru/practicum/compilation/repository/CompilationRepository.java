package ru.practicum.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilation.model.Compilation;

/**
 * Репозиторий для работы с сущностью Compilation.
 * Предоставляет стандартные методы CRUD (Create, Read, Update, Delete) для работы с подборками в базе данных.
 */
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
}
