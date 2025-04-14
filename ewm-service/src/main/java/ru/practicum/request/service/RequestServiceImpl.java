package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.error.exception.BadRequestDataException;
import ru.practicum.error.exception.UserNotFoundException;
import ru.practicum.event.dto.State;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatus;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

/**
 * Реализация сервисного интерфейса для работы с запросами на участие.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public List<RequestDto> getUserRequests(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Request> requests = requestRepository.findByUser(user);
        return requests.stream()
                .map(RequestMapper::toRequestDto)
                .toList();
    }

    @Transactional
    @Override
    public RequestDto createRequest(Integer userId, Integer eventId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow();
        checkRequestData(event, user);
        Request request = Request.builder()
                .user(user)
                .event(event)
                .status(processStatus(event))
                .build();
        Request result = requestRepository.save(request);
        return RequestMapper.toRequestDto(result);
    }

    @Transactional
    @Override
    public RequestDto cancelRequest(Integer userId, Integer requestId) {
        userRepository.findById(userId).orElseThrow();
        Request request = requestRepository.findById(requestId).orElseThrow();
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toRequestDto(request);
    }

    /**
     * Определяет статус запроса на участие в событии на основе настроек события.
     * Используется для автоматического установления статуса запроса при его создании.
     *
     * @param event Событие, на которое подается запрос.
     * @return Статус запроса (CONFIRMED или PENDING).
     */
    private RequestStatus processStatus(Event event) {
        if (!event.getRequestModeration()) {
            return RequestStatus.CONFIRMED;
        }
        if (event.getParticipantLimit() == 0) {
            return RequestStatus.CONFIRMED;
        }
        return RequestStatus.PENDING;
    }

    /**
     * Метод для проверки данных запроса на участие в событии.
     * Используется для валидации запроса перед его созданием.
     *
     * @param event Событие, на которое подается запрос.
     * @param user  Пользователь, подающий запрос.
     */
    private void checkRequestData(Event event, User user) {
        long count = event.getRequests().stream()
                .filter(it -> it.getStatus().equals(RequestStatus.CONFIRMED))
                .count();
        log.info("Количество запросов = {}", count);
        log.info("Лимит участников события  = {}", event.getParticipantLimit());
        if (user.equals(event.getInitiator())) {
            throw new BadRequestDataException("Создатель события не может делать запрос");
        } else if (!event.getState().equals(State.PUBLISHED)) {
            throw new BadRequestDataException("Событие должно быть опубликовано");
        } else if ((event.getParticipantLimit() > 0 && event.getParticipantLimit() <= count)) {
            throw new BadRequestDataException("Достигнут лимит по заявкам на данное событие");
        }
    }
}
