package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Класс представляет собой DTO (Data Transfer Object) для хранения статистических данных.
 * Он содержит следующие поля:
 * - app (имя приложения).
 * - uri (URI конечной точки).
 * - hits (количество обращений).
 */

@Data
@AllArgsConstructor
@Builder
public class StatsViewDto {
    private String app;
    private String uri;
    private int hits;
}
