package ru.practicum.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое при попытке доступа к подборке, которая не найдена в системе.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompilationNotFoundException extends RuntimeException {
    /**
     * Конструктор по умолчанию для исключения CompilationNotFoundException.
     * Создает исключение без дополнительного сообщения.
     */
    public CompilationNotFoundException() {
        super();
    }
}
