package ru.practicum.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое при попытке доступа к пользователю, который не найден в системе.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * Конструктор по умолчанию для исключения UserNotFoundException.
     * Создает исключение без дополнительного сообщения.
     */
    public UserNotFoundException() {
        super();
    }
}