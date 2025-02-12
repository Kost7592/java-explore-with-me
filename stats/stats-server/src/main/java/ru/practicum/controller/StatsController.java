package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsViewDto;
import ru.practicum.service.StatsService;

import java.util.List;

/**
 * Контроллер StatsController для работы со статистикой.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService service;

    /**
     * Метод для сохранения события в статистике
     *
     * @param event событие, которое необходимо сохранить
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public String saveEvent(@RequestBody EndpointHitDto event) {
        log.info("Получено событие для сохранения {}", event);
        service.saveEvent(event);
        return "Информация сохранена";
    }

    /**
     * Метод для получения общей статистики
     *
     * @param start начало временного диапазона, за который требуется статистика.
     * @param end конец временного диапазона, за который требуется статистика.
     * @param uris список URI, по которым требуется собрать статистику. Если параметр не указан, собираются данные по
     *             всем URI.
     * @param unique определяет, нужно ли выводить уникальные значения. Если он равен false (по умолчанию), выводятся
     *               все значения.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stats")
    public List<StatsViewDto> getStatistics(@RequestParam String start, @RequestParam String end,
                                            @RequestParam(required = false) List<String> uris,
                                            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Получен запрос на получение статистики с параметрами: start={}, end={}," +
                " uris={}, unique={}", start, end, uris, unique);
        return service.getStatistics(start, end, uris, unique);
    }
}
