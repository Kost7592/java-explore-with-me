package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@Builder
public class EndpointHitDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
