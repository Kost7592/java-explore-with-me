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
 * - confirmedRequests: Количество подтверждённых запросов на участие в событии
 * - eventDate: Дата и время проведения события.
 * - paid: Флаг, указывающий, является ли событие платным.
 * - title: Заголовок события.
 * - views: Количество просмотров события.
 * - category: Категория события в формате DTO.
 * - initiator: Пользователь, создавший событие, в кратком формате DTO.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortDto {
    private Integer id;
    private String annotation;
    private Long confirmedRequests;
    private String eventDate;
    private Boolean paid;
    private String title;
    private Integer views;
    private CategoryDto category;
    private UserShortDto initiator;
}
