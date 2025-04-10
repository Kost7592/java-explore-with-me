package ru.practicum.event.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.client.StatsBaseClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsViewDto;
import ru.practicum.error.exception.BadDataException;
import ru.practicum.error.exception.BadRequestDataException;
import ru.practicum.error.exception.UserNotFoundException;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatus;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static ru.practicum.event.dto.AdminStateAction.PUBLISH_EVENT;
import static ru.practicum.event.dto.State.*;
import static ru.practicum.event.dto.StateAction.SEND_TO_REVIEW;
import static ru.practicum.request.dto.RequestStatus.CONFIRMED;

/**
 * Реализация сервиса для работы с событиями.
 */
@Service
@ComponentScan(basePackages = {"ru.practicum.client"})
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final StatsBaseClient statsBaseClient;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventDto> getAllEvents(List<Integer> users, List<String> states, List<Integer> categories,
                                       String rangeStart, String rangeEnd, Integer from, Integer size) {
        List<Event> events = eventRepository.getEventsWithFilter(users, states, categories, rangeStart, rangeEnd);
        return events.stream()
                .skip(from)
                .limit(size)
                .map(EventMapper::toEventDto)
                .toList();
    }

    @Transactional
    @Override
    public EventDto updateEvent(Integer eventId, EventUpdateAdminRequest eventUpdateAdminRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        log.warn("Статус события: {}", event.getState());
        handleAdminRequestData(eventUpdateAdminRequest, event);
        return EventMapper.toEventDto(event);
    }

    @Override
    public List<EventShortDto> getPublicEvents(HttpServletRequest httpRequest, String text, List<Integer> categories,
                                               Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                               Sort sort, Integer from, Integer size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            startDateTime = isNull(rangeStart) ? LocalDateTime.now() : LocalDateTime.parse(rangeStart, formatter);
            endDateTime = isNull(rangeEnd) ? null : LocalDateTime.parse(rangeEnd, formatter);
            if (endDateTime != null && startDateTime.isAfter(endDateTime)) {
                throw new BadDataException("Дата окончания события не может быть раньше даты начала");
            }
        } catch (DateTimeParseException e) {
            throw new BadDataException("Неверный формат даты. Ожидаемый формат: yyyy-MM-dd HH:mm:ss");
        }
        text = isNull(text) ? null : text.toLowerCase();
        List<Event> events = eventRepository.getPublicEventsWithFilter(PUBLISHED, text, paid, rangeStart,
                rangeEnd);
        log.info("Получено {} событий", events.size());
        List<EventShortDto> result = events.stream()
                .filter(event -> {
                    if (onlyAvailable) {
                        long confirmedRequests = event.getRequests().stream()
                                .filter(request -> request.getStatus().equals(CONFIRMED))
                                .count();
                        return event.getParticipantLimit() > confirmedRequests;
                    }
                    return true;
                })
                .skip(from)
                .limit(size)
                .map(EventMapper::toEventShortDto)
                .toList();
        sendStatistic(httpRequest);
        return result;
    }

    @Override
    @Transactional
    public EventDto getEventById(Integer id, HttpServletRequest httpRequest) {
        Event event = eventRepository.findById(id).orElseThrow();
        if (PUBLISHED.equals(event.getState())) {
            sendStatistic(httpRequest);
            getStatistics(List.of(event));
            return EventMapper.toEventDto(event);
        }
        throw new NoSuchElementException("Не найдено опубликованное событие с id " + id);
    }

    @Override
    public List<EventShortDto> getUsersEvents(Integer userId, Integer from, Integer size) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Event> events = eventRepository.findAllByInitiator(user);
        return events.stream()
                .skip(from)
                .limit(size)
                .map(EventMapper::toEventShortDto)
                .toList();
    }

    @Override
    public EventDto createEvent(Integer userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow();
        Event event = EventMapper.toEvent(newEventDto);
        if (LocalDateTime.now().isAfter(LocalDateTime.parse(newEventDto.getEventDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) {
            throw new BadDataException("Время события не может быть прошедшее! dateTime: " + newEventDto.getEventDate());
        }
        event.setInitiator(user);
        event.setCategory(category);
        Event result = eventRepository.save(event);
        return EventMapper.toEventDto(result);
    }

    @Override
    public EventDto getFullInformation(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        return EventMapper.toEventDto(event);
    }

    @Transactional
    @Override
    public EventDto updateUsersEvent(Integer userId, Integer eventId, EventUpdateUserRequest eventRequest) {
        userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (!event.getState().equals(PUBLISHED)) {
            handleRequestData(eventRequest, event);
        } else {
            log.error("Запрос не соответствует требованиям : {}, событие :{}", eventRequest, event);
            throw new BadRequestDataException("Запрос не соответствует требованиям ");
        }
        return EventMapper.toEventDto(event);
    }

    @Override
    public List<RequestDto> getInfoAboutRequests(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        List<Request> requests = event.getRequests();
        return requests.stream()
                .map(RequestMapper::toRequestDto)
                .toList();
    }

    @Transactional
    @Override
    public EventStatusUpdateResult changeRequestStatus(Integer userId, Integer eventId,
                                                       EventStatusUpdateRequest request) {
        userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        List<Request> requests = event.getRequests().stream()
                .filter(element -> request.getRequestIds().contains(element.getId()))
                .toList();
        List<Request> requests1 = event.getRequests();
        log.error(" {}", requests1);
        boolean isPending = requests.stream()
                .allMatch(element -> element.getStatus().equals(RequestStatus.PENDING));
        if (!isPending) {
            log.error("В выборке не должно быть запросов кроме статуса PENDING , request : {}", requests);
            throw new BadRequestDataException("В выборке не должно быть запросов кроме статуса PENDING");
        }
        Integer participantLimit = event.getParticipantLimit();
        if (participantLimit == 0 || !event.getRequestModeration()) {
            log.error("Данное событие не требует подтверждения");
            throw new BadRequestDataException("Данное событие не требует подтверждения");
        }
        long countConfirmed = event.getRequests().stream()
                .filter(element -> element.getStatus().equals(CONFIRMED))
                .count();
        if (request.getStatus().equals(CONFIRMED)) {
            if ((requests.size() + countConfirmed) <= participantLimit) {
                requests.forEach(element -> element.setStatus(CONFIRMED));

                if ((requests.size() + countConfirmed) == participantLimit) {
                    event.getRequests().stream()
                            .filter(element -> element.getStatus().equals(RequestStatus.PENDING))
                            .forEach(element -> element.setStatus(RequestStatus.REJECTED));
                }
                List<Request> result = event.getRequests();
                return EventStatusUpdateResult.builder()
                        .confirmedRequests(result.stream()
                                .filter(element -> element.getStatus().equals(CONFIRMED))
                                .map(RequestMapper::toRequestDto)
                                .toList())
                        .rejectedRequests(result.stream()
                                .filter(element -> element.getStatus().equals(RequestStatus.REJECTED))
                                .map(RequestMapper::toRequestDto)
                                .toList())
                        .build();
            }
            log.error("Превышен лимит подтвержденных запросов");
            throw new BadRequestDataException("Превышен лимит подтвержденных запросов");
        } else if (request.getStatus().equals(RequestStatus.REJECTED)) {
            requests.forEach((element -> element.setStatus(RequestStatus.REJECTED)));
            List<Request> result = event.getRequests();
            return EventStatusUpdateResult.builder()
                    .confirmedRequests(result.stream()
                            .filter(element -> element.getStatus().equals(CONFIRMED))
                            .map(RequestMapper::toRequestDto)
                            .toList())
                    .rejectedRequests(result.stream()
                            .filter(element -> element.getStatus().equals(RequestStatus.REJECTED))
                            .map(RequestMapper::toRequestDto)
                            .toList())
                    .build();
        }
        log.error("Ошибка в статусе запроса");
        throw new BadRequestDataException("Ошибка в статусе запроса");
    }

    /**
     * Проверяет, что указанная дата и время находятся не менее чем за 2 часа от текущего момента.
     * Используется для валидации даты события перед её установкой.
     *
     * @param dateTime Строка, содержащая дату и время в формате "yyyy-MM-dd HH:mm:ss".
     * @return true, если дата корректна (не менее чем за 2 часа от текущего момента), иначе false.
     */
    private Boolean checkingDateTime(String dateTime) {
        LocalDateTime time = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long hours = Duration.between(LocalDateTime.now(), time).toHours();
        return hours >= 2;
    }

    /**
     * Метод для обработки данных запроса на обновление события пользователем.
     * Обновляет поля события на основе данных, предоставленных в запросе.
     *
     * @param request Запрос на обновление события, содержащий новые данные.
     * @param event   Событие, которое необходимо обновить.
     */
    private void handleRequestData(EventUpdateUserRequest request, Event event) {
        if (!isNull(request.getCategory())) {
            Category category = categoryRepository.findById(request.getCategory()).orElseThrow();
            event.setCategory(category);
        }
        if (!isNull(request.getAnnotation())) {
            event.setAnnotation(request.getAnnotation());
        }
        if (!isNull(request.getDescription())) {
            event.setDescription(request.getDescription());
        }
        if (!isNull(request.getEventDate())) {
            if (checkingDateTime(request.getEventDate())) {
                event.setEventDate(request.getEventDate());
            } else {
                log.error("Неверно указано время события {}", request.getEventDate());
                throw new BadDataException("Неверно указано время события");
            }
        }
        if (!isNull(request.getLocation())) {
            event.setLat(request.getLocation().getLat());
            event.setLon(request.getLocation().getLon());
        }
        if (!isNull(request.getPaid())) {
            event.setPaid(request.getPaid());
        }
        if (!isNull(request.getParticipantLimit())) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        if (!isNull(request.getRequestModeration())) {
            event.setRequestModeration(request.getRequestModeration());
        }
        if (!isNull(request.getTitle())) {
            event.setTitle(request.getTitle());
        }
        if (!isNull(request.getStateAction())) {
            event.setState(request.getStateAction().equals(SEND_TO_REVIEW) ? PENDING : CANCELED);
        }
    }

    /**
     * Метод для обработки данных запроса на обновление события администратором.
     * Обновляет поля события на основе данных, предоставленных в запросе.
     *
     * @param request Запрос на обновление события, содержащий новые данные.
     * @param event   Событие, которое необходимо обновить.
     */
    private void handleAdminRequestData(EventUpdateAdminRequest request, Event event) {
        if (!isNull(request.getCategory())) {
            Category category = categoryRepository.findById(request.getCategory()).orElseThrow();
            event.setCategory(category);
        }
        if (!isNull(request.getAnnotation())) {
            event.setAnnotation(request.getAnnotation());
        }
        if (!isNull((request.getTitle()))) {
            event.setTitle(request.getTitle());
        }
        if (!isNull((request.getPaid()))) {
            event.setPaid(request.getPaid());
        }
        if (!isNull(request.getLocation())) {
            event.setLat(request.getLocation().getLat());
            event.setLon(request.getLocation().getLon());
        }
        if (!isNull(request.getRequestModeration())) {
            event.setRequestModeration(request.getRequestModeration());
        }
        if (!isNull(request.getEventDate())) {
            if (LocalDateTime.now().isAfter(LocalDateTime.parse(request.getEventDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) {
                throw new BadDataException("Время события не может быть прошедшим! dateTime: " + request.getEventDate());
            }
            event.setEventDate(request.getEventDate());
        }
        if (!isNull(request.getDescription())) {
            event.setDescription(request.getDescription());
        }
        if (!isNull(request.getParticipantLimit())) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        if (!isNull(request.getStateAction())) {
            if (event.getState().equals(PENDING)) {
                event.setState(request.getStateAction().equals(PUBLISH_EVENT) ? PUBLISHED : CANCELED);
            } else {
                log.error("Событие уже опубликовано: {}", event.getState());
                throw new BadRequestDataException("Событие уже опубликовано");
            }
        }
    }

    /**
     * Метод для отправки статистики о посещении события.
     * Используется для регистрации факта обращения к событию в сервисе статистики.
     *
     * @param httpRequest Объект HttpServletRequest, содержащий информацию о запросе.
     */
    private void sendStatistic(HttpServletRequest httpRequest) {
        log.info("Отправляю статистику о посещении события httpRequest: {}", httpRequest);
        statsBaseClient.postHit(EndpointHitDto.builder()
                .app("ewm-service")
                .uri(httpRequest.getRequestURI())
                .ip(httpRequest.getLocalAddr())
                .timestamp(LocalDateTime.now())
                .build());
    }

    /**
     * Метод для получения статистики по обращениям к списку событий.
     *
     * @param events список событий по которым необходимо получить статистику.
     */
    public void getStatistics(List<Event> events) {
        List<String> uris = events.stream()
                .map(event -> String.format("/events/%s", event.getId()))
                .collect(Collectors.toList());
        List<StatsViewDto> stats = statsBaseClient.getStats("2000-01-01 00:00:00", "2100-01-01 00:00:00",
                uris, true);
        for (Event event : events) {
            StatsViewDto currentViewStats = stats.stream()
                    .filter(statsDto -> statsDto.getUri().equals(String.format("/events/%s", event.getId())))
                    .findFirst()
                    .orElse(null);
            Long views = (currentViewStats != null) ? currentViewStats.getHits() : 0;
            event.setViews(views.intValue());
            log.warn("Количество просмотров = {}", event.getViews());
        }
    }
}
