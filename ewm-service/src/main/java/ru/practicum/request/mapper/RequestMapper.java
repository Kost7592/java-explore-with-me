package ru.practicum.request.mapper;

import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;

/**
 * Класс для преобразования объектов Request в DTO-объекты RequestDto.
 */
public class RequestMapper {
    /**
     * Преобразует класс Request в класс RequestDto
     *
     * @param request объект события для преобразования.
     * @return преобразованный объект RequestDto.
     */
    public static RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .requester(request.getUser().getId())
                .created(request.getCreated().toString())
                .status(request.getStatus())
                .event(request.getEvent().getId())
                .build();
    }
}
