package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое при обнаружении некорректных параметров в запросе.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadParametersException extends RuntimeException {
    /**
     * Передает сообщение об ошибке в конструктор RuntimeException
     *
     * @param message сообщение об ошибке, которое будет передано в родительский класс RuntimeException.
     */
    public BadParametersException(String message) {
    }
}
