package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

/**
 * DTO (Data Transfer Object) для представления результата обновления статуса запросов на участие в событии.
 * Используется для возврата информации о подтверждённых и отклонённых запросах.
 * <p>
 * Поля:
 * - confirmedRequests: Список запросов на участие, которые были подтверждены.
 * - rejectedRequests: Список запросов на участие, которые были отклонены.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventStatusUpdateResult {
    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;
}

