package ru.practicum.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventDto;
import ru.practicum.event.dto.EventUpdateAdminRequest;
import ru.practicum.event.service.EventService;

import java.util.List;

/**
 * Класс AdminEventController контроллер для управления событиями в административной панели.
 */
@RestController
@RequestMapping("/admin/events")
@Slf4j
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;

    /**
     * Возвращает список событий с возможностью фильтрации по различным параметрам и пагинацией.
     *
     * @param users      Список идентификаторов пользователей, чьи события нужно включить в результат (опционально).
     * @param states     Список состояний событий, по которым нужно фильтровать (опционально).
     * @param categories Список идентификаторов категорий, по которым нужно фильтровать события (опционально).
     * @param rangeStart Начальная дата диапазона для фильтрации событий по времени (опционально, в формате строки).
     * @param rangeEnd   Конечная дата диапазона для фильтрации событий по времени (опционально, в формате строки).
     * @param from       Начальная позиция списка событий для пагинации (по умолчанию 0).
     * @param size       Количество событий в списке для пагинации (по умолчанию 10).
     * @return Список событий, соответствующих заданным критериям, в формате DTO.
     */
    @GetMapping
    public List<EventDto> getAllEvents(@RequestParam(required = false) List<Integer> users,
                                       @RequestParam(required = false) List<String> states,
                                       @RequestParam(required = false) List<Integer> categories,
                                       @RequestParam(required = false) String rangeStart,
                                       @RequestParam(required = false) String rangeEnd,
                                       @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                       @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Получен запрос на получение отфильтрованных событий.");
        return eventService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Обновляет событие по его идентификатору с использованием данных, предоставленных администратором.
     *
     * @param eventId                 Идентификатор события, которое необходимо обновить. Должно быть положительным числом (минимум 1).
     * @param eventUpdateAdminRequest ventUpdateAdminRequest: DTO с данными для обновления события. Должно быть валидным
     *                                (аннотация @Valid).
     * @return Обновлённое событие в формате DTO.
     */
    @PatchMapping("/{eventId}")
    public EventDto updateEvent(@PathVariable @Min(1) Integer eventId,
                                @RequestBody @Valid EventUpdateAdminRequest eventUpdateAdminRequest) {
        log.info("Получен запрос на обновление события c eventId: {}, eventUpdateAdminRequest: {} ", eventId, eventUpdateAdminRequest);
        EventDto eventDto = eventService.updateEvent(eventId, eventUpdateAdminRequest);
        log.info("Обновлено событие: {} ", eventDto);
        return eventDto;
    }
}
