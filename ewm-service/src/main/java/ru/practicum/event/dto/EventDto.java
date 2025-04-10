package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

/**
 * DTO (Data Transfer Object) для представления данных о событии.
 * Используется для передачи информации о событии между слоями приложения.
 * <p>
 * Поля:
 * - id: Уникальный идентификатор события.
 * - annotation: Краткое описание события.
 * - category: Категория события в формате DTO.
 * - confirmedRequests: Количество подтверждённых запросов на участие в событии.
 * - createdOn: Дата и время создания события.
 * - description: Полное описание события.
 * - eventDate: Дата и время проведения события.
 * - initiator: Пользователь, создавший событие, в кратком формате DTO.
 * - location: Местоположение события.
 * - paid: Флаг, указывающий, является ли событие платным.
 * - participantLimit: Ограничение на количество участников события.
 * - publishedOn: Дата и время публикации события.
 * - requestModeration: Флаг, указывающий, требуется ли модерация запросов на участие.
 * - state: Текущее состояние события (например, PENDING, PUBLISHED, CANCELED).
 * - title: Заголовок события.
 * - views: Количество просмотров события.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private Integer id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Integer views;
}
