package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий географическое местоположение.
 * Используется для хранения координат широты и долготы.
 * <p>
 * Поля:
 * - lat: Широта местоположения.
 * - lon: Долгота местоположения.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private float lat;
    private float lon;
}