package ru.practicum.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.service.EventService;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

/**
 * Класс PrivateEventController контроллер для управления событиями в приватной панели.
 */
@RequestMapping("/users")
@RestController
@Slf4j
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;

    /**
     * Возвращает список событий, созданных конкретным пользователем, с поддержкой пагинации.
     * <p>
     *
     * @param userId Идентификатор пользователя, чьи события нужно получить. Должно быть положительным числом (минимум 1).
     * @param from   Начальная  позиция списка событий для пагинации (по умолчанию 0).
     * @param size   Количество событий в списке для пагинации (по умолчанию 10).
     */
    @GetMapping("/{userId}/events")
    public List<EventShortDto> getUsersEvents(@PathVariable @Min(1) Integer userId,
                                              @RequestParam(defaultValue = "0") Integer from,
                                              @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getUsersEvents(userId, from, size);
    }

    /**
     * Этот метод создает новое событие для указанного пользователя.
     *
     * @param userId  идентификатор пользователя, для которого создается событие
     * @param request объект, содержащий данные о новом событии
     * @return созданный объект EventDto
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/events")
    public EventDto createEvent(@PathVariable Integer userId,
                                @RequestBody @Valid NewEventDto request) {
        log.info("Получен запрос на создание события с userId: {}, event: {}", userId, request);
        EventDto event = eventService.createEvent(userId, request);
        log.info("Событие создано. event : {}", event);
        return event;
    }

    /**
     * Этот метод возвращает полную информацию о событии для указанного пользователя.
     *
     * @param userId  идентификатор пользователя, которому принадлежит событие
     * @param eventId идентификатор события, для которого требуется получить информацию
     * @return объект EventDto, содержащий полную информацию о событии
     */
    @GetMapping("/{userId}/events/{eventId}")
    public EventDto getFullInformation(@PathVariable Integer userId,
                                       @PathVariable Integer eventId) {
        return eventService.getFullInformation(userId, eventId);
    }

    /**
     * Этот метод обновляет информацию о событии для указанного пользователя.
     *
     * @param userId  идентификатор пользователя, которому принадлежит событие
     * @param eventId идентификатор события, которое нужно обновить
     * @param event   объект, содержащий обновлённые данные о событии
     * @return обновлённый объект EventDto
     */
    @PatchMapping("/{userId}/events/{eventId}")
    public EventDto updateEvent(@PathVariable Integer userId,
                                @PathVariable Integer eventId,
                                @RequestBody @Valid EventUpdateUserRequest event) {
        log.info("Получен запрос на обновление события. userId: {}, eventId: {}, event: {}.", userId, eventId, event);
        return eventService.updateUsersEvent(userId, eventId, event);
    }

    /**
     * Этот метод возвращает информацию о запросах на участие в событии для указанного пользователя.
     *
     * @param userId  идентификатор пользователя, которому принадлежит событие
     * @param eventId идентификатор события, для которого требуется получить информацию о запросах
     * @return список объектов RequestDto, содержащих информацию о запросах на участие в событии
     */
    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> getInfoAboutRequests(@PathVariable Integer userId,
                                                 @PathVariable Integer eventId) {
        return eventService.getInfoAboutRequests(userId, eventId);
    }

    /**
     * Этот метод обновляет статус заявки на участие в событии для указанного пользователя.
     *
     * @param userId  идентификатор пользователя, которому принадлежит событие
     * @param eventId идентификатор события, для которого требуется обновить статус заявки
     * @param request объект, содержащий данные для обновления статуса заявки
     * @return результат обновления статуса заявки
     */
    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventStatusUpdateResult changeRequestStatus(@PathVariable Integer userId,
                                                       @PathVariable Integer eventId,
                                                       @RequestBody @Valid EventStatusUpdateRequest request) {
        log.info("Получен запрос на обновление статуса заявки userId: {}, eventId: {}, request: {} ", userId, eventId, request);
        return eventService.changeRequestStatus(userId, eventId, request);
    }
}
