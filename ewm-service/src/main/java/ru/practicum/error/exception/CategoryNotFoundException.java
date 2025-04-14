package ru.practicum.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое при попытке доступа к категории, которая не найдена в системе.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {
    /**
     * Конструктор по умолчанию для исключения CategoryNotFoundException.
     * Создает исключение без дополнительного сообщения.
     */
    public CategoryNotFoundException() {
        super();
    }
}