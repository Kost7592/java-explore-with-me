package ru.practicum.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс ErrorResponse представляет информацию об ошибке.
 * Используется для стандартизированного представления ошибок в API.
 * <p>
 * Поля:
 * - errors: Список ошибок, содержащий детализированную информацию о каждой ошибке.
 * - message: Сообщение об ошибке.
 * - reason: Причина ошибки.
 * - status: Статус ошибки (например, "NOT_FOUND", "BAD_REQUEST").
 * - timestamp: Временная метка, указывающая, когда произошла ошибка. Форматируется как "yyyy-MM-dd HH:mm:ss".
 */
@Getter
@Builder
public class ErrorResponse {
    private final List<java.lang.Error> errors;
    private final String message;
    private final String reason;
    private final String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
}
