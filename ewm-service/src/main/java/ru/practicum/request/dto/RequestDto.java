package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для представления данных о запросе на участие в событии.
 * Используется для передачи информации о запросе между слоями приложения.
 * <p>
 * Поля:
 * - created: Дата и время создания запроса.
 * - event: Идентификатор события, на которое подается запрос.
 * - id: Уникальный идентификатор запроса.
 * - requester: Идентификатор пользователя, подавшего запрос.
 * - status: Текущий статус запроса (например, PENDING, CONFIRMED, REJECTED).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {
    private String created;
    private Integer event;
    private Integer id;
    private Integer requester;
    private RequestStatus status;
}