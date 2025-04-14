package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsViewDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Класс StatsStatsBaseClient представляет собой клиент для работы со статистическими данными.
 * Он содержит следующие поля: restTemplate (шаблон для отправки HTTP-запросов), serverUrl (URL сервера статистики),
 * formatter (формат даты и времени).
 * Класс используется для получения статистических данных с сервера статистики.
 */
@Service
@RequiredArgsConstructor
public class StatsBaseClient {
    final RestTemplate restTemplate = new RestTemplate();
    @Value("${stats-server.url}")
    private String statsServerUrl;

    /**
     * Этот метод отправляет запрос к эндпоинту для записи информации о запросе.
     * Он принимает объект типа EndpointHitDto, который содержит информацию о запросе.
     * Метод использует RestTemplate для отправки POST-запроса к указанной конечной точке.
     */
    public void postHit(EndpointHitDto hit) {
        HttpEntity<EndpointHitDto> requestEntity = new HttpEntity<>(hit);
        restTemplate.exchange(statsServerUrl + "/hit", HttpMethod.POST, requestEntity, String.class).getBody();
    }

    /**
     * Этот метод используется для получения статистических данных за определённый период времени по указанным URI.
     * Он принимает параметры start и end типа String, которые задают временной диапазон для выборки данных,
     * список urisList типа List<String> с URI, по которым требуется собрать статистику,
     * и параметр unique типа Boolean, который определяет, нужно ли выводить уникальные значения.
     * Метод использует RestTemplate для отправки GET-запроса к указанной конечной точке.
     */
    public List<StatsViewDto> getStats(String start, String end, List<String> urisList, Boolean unique) {
        String uris = String.join(",", urisList);
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique != null ? unique : false
        );
        StatsViewDto[] result = restTemplate.getForObject(statsServerUrl + "/stats?start={start}&end={end}" +
                "&uris={uris}&unique={unique}", StatsViewDto[].class, parameters);
        return isNull(result) ? Collections.emptyList() : List.of(result);
    }
}
