package ru.practicum.mapper;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

/**
 * Класс-маппер для работы с данными статистики.
 */
public class StatsMapper {
    /**
     * Этот метод преобразует объект EndpointHit в объект типа EndpointHitDto.
     *
     * @param hit объект hit, содержащий информацию о запросе к конечной точке.
     */
    public static EndpointHitDto toEndpointHitDto(EndpointHit hit) {
        return EndpointHitDto.builder()
                .app(hit.getApp())
                .id(hit.getId())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp())
                .uri(hit.getUri())
                .build();
    }

    /**
     * Этот метод преобразует объект EndpointHitDto в объект типа EndpointHit.
     *
     * @param hit объект hit, содержащий информацию о запросе к конечной точке.
     */
    public static EndpointHit toEndpointHit(EndpointHitDto hit) {
        return EndpointHit.builder()
                .app(hit.getApp())
                .id(hit.getId())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp())
                .uri(hit.getUri())
                .build();
    }
}
