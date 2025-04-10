package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;

import java.util.List;

/**
 * Сервис для работы с подборками.
 * Определяет методы для выполнения бизнес-логики, связанной с подборками.
 */
public interface CompilationService {
    /**
     * Создает новую подборку на основе переданных данных.
     *
     * @param compilationDto DTO с данными для создания подборки.
     * @return Созданная подборка в формате DTO.
     */
    CompilationDto createCompilation(NewCompilationDto compilationDto);

    /**
     * Обновляет данные подборки по её идентификатору.
     *
     * @param updateCompilationDto DTO с данными для обновления подборки. Должно быть валидным (аннотация @Valid).
     * @param compId               Идентификатор подборки, которую необходимо обновить.
     * @return Обновлённая подборка в формате DTO.
     */
    CompilationDto updateCompilation(UpdateCompilationDto updateCompilationDto, Integer compId);

    /**
     * Удаляет подборку по ее идентификатору.
     *
     * @param compId идентификатор удаляемой подборки.
     */
    void deleteCompilation(Integer compId);

    /**
     * Возвращает подборку по ее идентификатору.
     *
     * @param compId идентификатор подборки.
     * @return DTO с данными возвращенной подборки.
     */
    CompilationDto getCompilationById(Integer compId);

    /**
     * Возвращает список всех подборок с возможностью фильтрации и пагинации.
     *
     * @param pinned Опциональный параметр для фильтрации подборок по закреплённости (true/false).
     * @param from   Опциональный параметр для указания начальной позиции списка (по умолчанию 0).
     * @param size   Опциональный параметр для указания количества элементов в списке (по умолчанию 10).
     * @return Список подборок в формате DTO.
     */
    List<CompilationDto> getAllEvents(Boolean pinned, Integer from, Integer size);
}