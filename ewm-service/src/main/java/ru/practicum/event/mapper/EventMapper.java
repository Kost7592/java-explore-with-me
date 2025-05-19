package ru.practicum.event.mapper;

import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;
import ru.practicum.request.model.Request;
import ru.practicum.user.mapper.UserMapper;

import java.util.List;

import static java.util.Objects.isNull;
import static ru.practicum.request.dto.RequestStatus.CONFIRMED;

/**
 * Маппер для преобразования объектов, связанных с событиями, между различными представлениями (DTO и сущностями).
 */
public class EventMapper {
    /**
     * Преобразует сущность Event в EventShortDto
     *
     * @param event сущность Event.
     * @return EventShortDto, содержащий данные из сущности Event.
     */
    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .paid(event.getPaid())
                .annotation(event.getAnnotation())
                .views(event.getViews())
                .title(event.getTitle())
                .category(CategoryMapper.categoryDto(event.getCategory()))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .confirmedRequests(event.getRequests().stream()
                        .filter(request -> request.getStatus().equals(CONFIRMED))
                        .count())
                .build();
    }

    /**
     * Преобразует сущность Event в сущность EventDto
     *
     * @param event сущность Event.
     * @return EventDto, содержащий данные из сущности Event.
     */
    public static EventDto toEventDto(Event event) {
        List<Request> requests = event.getRequests();

        return EventDto.builder()
                .id(event.getId())
                .description(event.getDescription())
                .title(event.getTitle())
                .paid(event.getPaid())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(new Location(event.getLat(), event.getLon()))
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn())
                .state(event.getState())
                .participantLimit(event.getParticipantLimit())
                .eventDate(event.getEventDate())
                .views(event.getViews())
                .category(CategoryMapper.categoryDto(event.getCategory()))
                .requestModeration(event.getRequestModeration())
                .annotation(event.getAnnotation())
                .confirmedRequests(isNull(requests) ? 0 : requests.stream()
                        .filter(request -> CONFIRMED.equals(request.getStatus()))
                        .count())
                .comments(isNull(event.getComments()) ? null : event.getComments().stream()
                        .map(CommentMapper::toCommentShortDto)
                        .toList())
                .build();
    }

    /**
     * Преобразует сущность newEventDto в сущность Event
     *
     * @param newEventDto сущность NewEventDto.
     * @return Event, содержащий данные из сущности NewEventDto.
     */
    public static Event toEvent(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .title(newEventDto.getTitle())
                .paid(!isNull(newEventDto.getPaid()) && newEventDto.getPaid())
                .state(State.PENDING)
                .participantLimit(isNull(newEventDto.getParticipantLimit()) ? 0 : newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .lon(newEventDto.getLocation().getLon())
                .lat(newEventDto.getLocation().getLat())
                .views(0)
                .build();
    }
}
