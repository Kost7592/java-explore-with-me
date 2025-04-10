package ru.practicum.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.request.dto.RequestStatus;

import java.util.List;

/**
 * DTO (Data Transfer Object) для обновления статуса запросов на участие в событии.
 * Используется для массового изменения статуса запросов на участие.
 * <p>
 * Поля:
 * - requestIds: Список идентификаторов запросов на участие, которые необходимо обновить. Не может быть null.
 * - status: Новый статус запросов на участие. Не может быть null.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventStatusUpdateRequest {
    @NotNull
    private List<Integer> requestIds;
    @NotNull
    private RequestStatus status;
}
