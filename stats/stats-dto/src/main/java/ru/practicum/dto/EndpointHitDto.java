package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс представляет собой DTO (Data Transfer Object) для хранения информации о запросах к конечным точкам.
 * Он содержит следующие поля:
 * - id (уникальный идентификатор запроса).
 * - app (имя приложения).
 * - uri (URI конечной точки).
 * - ip (IP-адрес клиента).
 * - timestamp (дата и время запроса).
 */
@Data
@AllArgsConstructor
@Builder
public class EndpointHitDto {
    private int id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
