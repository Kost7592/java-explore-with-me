package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsViewDto;

import java.util.List;

/**
 * Интерфейс StatsService представляет сервис для работы со статистическими данными.
 */
public interface StatsService {
    /**
     * Метод сохраняет событие в базе данных.
     *
     * @param event объект типа EndpointHitDto, который содержит информацию о событии
     */
    void saveEvent(EndpointHitDto event);

    /**
     * Метод для получения общей статистики из базы данных.
     *
     * @param start  начало временного диапазона, за который требуется статистика.
     * @param end    конец временного диапазона, за который требуется статистика.
     * @param uris   список URI для которого был осуществлен запрос
     * @param unique определяет, нужно ли выводить уникальные значения. Если он равен false (по умолчанию), выводятся
     *               все значения.
     */
    List<StatsViewDto> getStatistics(String start, String end, List<String> uris, Boolean unique);
}
