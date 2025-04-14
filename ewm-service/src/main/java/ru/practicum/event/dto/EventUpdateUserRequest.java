package ru.practicum.event.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * DTO (Data Transfer Object) для обновления события пользователем.
 * Используется для передачи данных, необходимых для обновления события пользователем.
 * <p>
 * Поля:
 * - annotation: Краткое описание события. Должно содержать от 20 до 2000 символов.
 * - category: Идентификатор категории события.
 * - description: Полное описание события. Должно содержать от 20 до 7000 символов.
 * - eventDate: Дата и время проведения события.
 * - location: Местоположение события. Должно быть валидным (аннотация @Valid).
 * - paid: Флаг, указывающий, является ли событие платным.
 * - participantLimit: Ограничение на количество участников события. Должно быть положительным числом или нулём.
 * - requestModeration: Флаг, указывающий, требуется ли модерация запросов на участие.
 * - stateAction: Действие пользователя для изменения состояния события (например, отправка на модерацию или отмена).
 * - title: Заголовок события. Должен содержать от 3 до 120 символов.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateUserRequest {
    @Length(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    @Length(min = 20, max = 7000)
    private String description;
    private String eventDate;
    @Valid
    private Location location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120)
    private String title;
}
