package ru.practicum.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.Sort;
import ru.practicum.event.service.EventService;

import java.util.List;

/**
 * Класс PublicEventController контроллер для управления событиями.
 */
@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService eventService;

    /**
     * Возвращает список публичных событий с возможностью фильтрации и сортировки.
     *
     * @param httpRequest   Объект HttpServletRequest, содержащий информацию о запросе (например, IP-адрес клиента).
     * @param text          Текст для поиска в аннотации и описании событий (опционально).
     * @param categories    Список идентификаторов категорий для фильтрации событий (опционально).
     * @param paid          Флаг, указывающий, являются ли события платными (true/false, опционально).
     * @param rangeStart    Начальная дата диапазона для фильтрации событий по времени (опционально, в формате строки).
     * @param rangeEnd      Конечная дата диапазона для фильтрации событий по времени (опционально, в формате строки).
     * @param onlyAvailable Флаг, указывающий, что нужно возвращать только события с доступными местами (по умолчанию
     *                      false).
     * @param sort          Параметр сортировки событий (например, по дате или количеству просмотров, опционально).
     * @param from          Начальная позиция списка событий для пагинации (по умолчанию 0).
     * @param size          Количество событий в списке для пагинации (по умолчанию 10).
     * @return Список публичных событий в кратком формате DTO, соответствующих заданным критериям.
     */
    @GetMapping
    public List<EventShortDto> getPublicEvents(HttpServletRequest httpRequest,
                                               @RequestParam(required = false) String text,
                                               @RequestParam(required = false) List<Integer> categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @RequestParam(required = false) String rangeStart,
                                               @RequestParam(required = false) String rangeEnd,
                                               @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                               @RequestParam(required = false) Sort sort,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получен запрос на получение публичных событий. Categories: {}", categories);
        return eventService.getPublicEvents(httpRequest, text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
    }

    /**
     * Этот метод возвращает событие по его идентификатору.
     *
     * @param id          идентификатор события, которое нужно получить
     * @param httpRequest объект HttpServletRequest, содержащий информацию о HTTP-запросе
     * @return объект EventDto, содержащий информацию о событии
     */
    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable Integer id, HttpServletRequest httpRequest) {
        log.info("Получен запрос на получение события по его id : {}", id);
        EventDto eventById = eventService.getEventById(id, httpRequest);
        log.info("Получено событие : {}", eventById);
        return eventById;
    }
}
