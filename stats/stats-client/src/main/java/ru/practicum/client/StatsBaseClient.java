package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsViewDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Класс StatsBaseClient представляет собой клиент для работы со статистическими данными.
 * Он содержит следующие поля: restTemplate (шаблон для отправки HTTP-запросов), serverUrl (URL сервера статистики),
 * formatter (формат даты и времени).
 * Класс используется для получения статистических данных с сервера статистики.
 */
@Service
@RequiredArgsConstructor
public class StatsBaseClient {
    private final RestTemplate restTemplate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Этот метод отправляет запрос к эндпоинту для записи информации о запросе.
     * Он принимает объект типа EndpointHitDto, который содержит информацию о запросе.
     * Метод использует RestTemplate для отправки POST-запроса к указанной конечной точке.
     */
    public EndpointHitDto postHit(EndpointHitDto hit) {
        HttpEntity<EndpointHitDto> requestEntity = new HttpEntity<>(hit);
        return restTemplate.exchange("http://stats-server:909" + "/hit", HttpMethod.POST, requestEntity,
                EndpointHitDto.class).getBody();
    }

    /**
     * Этот метод используется для получения статистических данных за определённый период времени по указанным URI.
     * Он принимает параметры start и end типа LocalDateTime, которые задают временной диапазон для выборки данных,
     * список urisList типа List<String> с URI, по которым требуется собрать статистику,
     * и параметр unique типа Boolean, который определяет, нужно ли выводить уникальные значения.
     * Метод использует RestTemplate для отправки GET-запроса к указанной конечной точке.
     */
    public List<StatsViewDto> getStats(LocalDateTime start, LocalDateTime end, List<String> urisList, Boolean unique) {
        String startDayTime = start.format(formatter);
        String endDayTime = end.format(formatter);
        String uris = String.join(",", urisList);
        Map<String, Object> parameters = Map.of(
                "start", startDayTime,
                "end", endDayTime,
                "uris", uris,
                "unique", unique != null ? unique : false
        );
        return restTemplate.getForObject("http://stats-server:909" + "/stats?start={start}&end={end}&uris=" +
                        "{uris}&unique={unique}",
                List.class, parameters);
    }
}
