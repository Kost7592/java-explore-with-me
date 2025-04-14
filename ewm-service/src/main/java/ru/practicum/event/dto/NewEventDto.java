package ru.practicum.event.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * DTO (Data Transfer Object) для создания нового события.
 * Используется для передачи данных, необходимых для создания события.
 * <p>
 * Поля:
 * - annotation: Краткое описание события. Не может быть пустым и должно содержать от 20 до 2000 символов.
 * - category: Идентификатор категории события. Не может быть null и должен быть положительным числом.
 * - description: Полное описание события. Не может быть пустым и должно содержать от 20 до 7000 символов.
 * - eventDate: Дата и время проведения события.
 * - location: Местоположение события. Не может быть null и должно быть валидным (аннотация @Valid).
 * - paid: Флаг, указывающий, является ли событие платным.
 * - participantLimit: Ограничение на количество участников события. Должно быть положительным числом или нулём.
 * - requestModeration: Флаг, указывающий, требуется ли модерация запросов на участие. По умолчанию true.
 * - title: Заголовок события. Не может быть null и должен содержать от 3 до 120 символов.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;
    @NotNull
    @Positive
    private Integer category;
    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;
    private String eventDate;
    @NotNull
    @Valid
    private Location location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration = true;
    @NotNull
    @Length(min = 3, max = 120)
    private String title;
}
