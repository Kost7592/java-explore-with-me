package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

/**
 * Класс RequestController контроллер для управления запросами в событиях.
 */
@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    /**
     * Метод для получения запросом определённого пользователя
     *
     * @param userId идентификатор пользователя.
     * @return список запросов в формате RequestDto.
     */
    @GetMapping("/{userId}/requests")
    public List<RequestDto> getUserRequests(@PathVariable Integer userId) {
        return requestService.getUserRequests(userId);
    }

    /**
     * Метод для создания нового запроса пользователя в событии.
     *
     * @param userId  идентификатор пользователя.
     * @param eventId идентификатор события.
     * @return запрос на участие в событии в формате RequestDto
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/requests")
    public RequestDto createRequest(@PathVariable Integer userId,
                                    @RequestParam Integer eventId) {
        log.info("Получен запрос на участии в событии userId:{},eventId:{}", userId, eventId);
        return requestService.createRequest(userId, eventId);
    }

    /**
     * Отмена запроса на участие в событии.
     *
     * @param userId    идентификатор пользователя
     * @param requestId идентификатор запроса.
     * @return отмененный запрос в формате RequestDto
     */
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Integer userId,
                                    @PathVariable Integer requestId) {
        return requestService.cancelRequest(userId, requestId);
    }
}
