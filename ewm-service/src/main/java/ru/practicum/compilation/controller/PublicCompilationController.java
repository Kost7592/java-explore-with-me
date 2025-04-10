package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import java.util.List;

/**
 * Контроллер для обработки публичных запросов, связанных с подборками.
 */
@RestController
@RequestMapping("/compilations")
@Slf4j
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    /**
     * Возвращает список всех подборок с возможностью фильтрации и пагинации.
     *
     * @param pinned Опциональный параметр для фильтрации подборок по закреплённости (true/false).
     * @param from   Опциональный параметр для указания начальной позиции списка (по умолчанию 0).
     * @param size   Опциональный параметр для указания количества элементов в списке (по умолчанию 10).
     * @return Список подборок в формате DTO.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getAllEvents(@RequestParam(required = false) Boolean pinned,
                                             @RequestParam(required = false, defaultValue = "0") Integer from,
                                             @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Получен запрос на получение списка всех событий.");
        return compilationService.getAllEvents(pinned, from, size);
    }

    /**
     * Возвращает информацию о подборке по её идентификатору.
     *
     * @param compId Идентификатор подборки, которую необходимо получить.
     * @return Подборка в формате DTO.
     */
    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Integer compId) {
        log.info("Получен запрос на получение списка событий по его id.");
        return compilationService.getCompilationById(compId);
    }
}
