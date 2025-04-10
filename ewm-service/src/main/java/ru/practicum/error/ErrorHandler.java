package ru.practicum.error;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.error.exception.BadDataException;
import ru.practicum.error.exception.BadRequestDataException;

import java.util.NoSuchElementException;

/**
 * Класс ErrorHandler обрабатывает исключения, возникающие в процессе работы приложения.
 * Он предоставляет централизованное место для обработки исключений и позволяет возвращать согласованные ответы об
 * ошибках пользователям.
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    /**
     * Обрабатывает исключение ObjectNotFoundException, возвращая ответ с кодом 404 (Not Found).
     * В ответе содержится сообщение об ошибке, полученное из исключения.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(ObjectNotFoundException e) {
        log.debug("Получен статус 404 Not found {}", e.getMessage(), e);
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .message(e.getMessage())
                .reason("Необходимый объект не найден.")
                .build();
    }

    /**
     * Обрабатывает исключение BadRequestDataException, возвращая ответ с кодом 409 (Conflict).
     * В ответе содержится сообщение об ошибке, полученное из исключения.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerEmailConflictException(final BadRequestDataException e) {
        log.debug("Получен статус 409 CONFLICT {}", e.getMessage(), e);

        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.toString())
                .message(e.getMessage())
                .reason("Нарушено ограничение целостности.")
                .build();
    }

    /**
     * Обрабатывает исключение BadDataException, возвращая ответ с кодом 400 (Bad Request).
     * В ответе содержится сообщение об ошибке, полученное из исключения.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerEmailConflictException(final BadDataException e) {
        log.debug("Получен статус 409 CONFLICT {}", e.getMessage(), e);

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(e.getMessage())
                .reason("Нарушено ограничение целостности.")
                .build();
    }

    /**
     * Обрабатывает исключение NoSuchElementException, возвращая ответ с кодом 400 (Not Found).
     * В ответе содержится сообщение об ошибке, полученное из исключения.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerIncorrectParametersException(NoSuchElementException e) {
        log.debug("Получен статус 404 NOT FOUND {}", e.getMessage(), e);
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .message(e.getMessage())
                .reason("Неверные параметры")
                .build();
    }

    /**
     * Обрабатывает исключение PSQLException, возвращая ответ с кодом 409 (Conflict).
     * В ответе содержится сообщение об ошибке, полученное из исключения.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerSQLException(PSQLException e) {
        log.debug("Получен статус 409 CONFLICT {}", HttpStatus.CONFLICT, e.getMessage());
        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.toString())
                .message(e.getMessage())
                .reason("Запрос вызвал конфликт")
                .build();
    }
}