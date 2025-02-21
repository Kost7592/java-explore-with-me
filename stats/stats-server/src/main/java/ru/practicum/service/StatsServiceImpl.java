package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsViewDto;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс StatsServiceImpl реализация интерфейса StatsService для работы со статистикой.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void saveEvent(EndpointHitDto event) {
        EndpointHit endpointHit = StatsMapper.toEndpointHit(event);
        EndpointHit savedResult = repository.save(endpointHit);
        log.info("Событие сохранено в общую статистику {}", savedResult);
    }

    @Override
    public List<StatsViewDto> getStatistics(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime parsedStart = LocalDateTime.parse(start, formatter);
        LocalDateTime parsedEnd = LocalDateTime.parse(end, formatter);
        if (uris == null) {
            log.info("Получена статистика за период между {} и {}", start, end);
            return convertToViewStatsDto(repository.findAllElements(parsedStart, parsedEnd, unique));
        }
        log.info("Получена статистика за период между {} и {}", start, end);
        return convertToViewStatsDto(repository.findAllElementsWithUris(parsedStart, parsedEnd, uris, unique));
    }

    /**
     * Метод convertToViewStatsDto преобразует список карт с данными статистики в список объектов StatsViewDto.
     *
     * @param result список карт result, где каждая карта содержит поля app, uri и hits.
     */
    private List<StatsViewDto> convertToViewStatsDto(List<Map<String, Object>> result) {
        return result.stream()
                .map(map -> new StatsViewDto((String) map.get("app"), (String) map.get("uri"),
                        (Long) map.get("hits")))
                .collect(Collectors.toList());
    }
}
