package ru.practicum.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое при обнаружении некорректных или конфликтующих данных.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BadDataException extends RuntimeException {
    /**
     * Передает сообщение об ошибке в конструктор RuntimeException
     *
     * @param message сообщение об ошибке, которое будет передано в родительский класс RuntimeException.
     */
    public BadDataException(String message) {
    }
}
