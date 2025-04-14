package ru.practicum.request.service;

import ru.practicum.request.dto.RequestDto;

import java.util.List;

/**
 * Сервисный интерфейс для работы с запросами на участие в событии.
 */
public interface RequestService {
    /**
     * Метод создает запрос на участие в событии.
     *
     * @param userId  идентификатор пользователя.
     * @param eventId идентификатор события.
     * @return созданный запрос RequestDto.
     */
    RequestDto createRequest(Integer userId, Integer eventId);

    /**
     * Метод возвращающий все запросы пользователя.
     *
     * @param userId идентификатор пользователя.
     * @return список запросов пользователя RequestDto
     */
    List<RequestDto> getUserRequests(Integer userId);

    /**
     * Метод, отменяющий запрос пользователя.
     *
     * @param userId    идентификатор пользователя.
     * @param requestId идентификатор запроса.
     * @return отмененный запрос RequestDto
     */
    RequestDto cancelRequest(Integer userId, Integer requestId);
}
