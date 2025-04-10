package ru.practicum.event.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.event.dto.*;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

/**
 * Сервис для работы с событиями.
 * Определяет методы для выполнения бизнес-логики, связанной с событиями.
 */
public interface EventService {

    /**
     * Возвращает список событий с возможностью фильтрации по различным параметрам и пагинацией.
     * <p>
     * Параметры:
     *
     * @param users      Список идентификаторов пользователей, чьи события нужно включить в результат. Если null, фильтр не применяется.
     * @param states     Список состояний событий, по которым нужно фильтровать. Если null, фильтр не применяется.
     * @param categories Список идентификаторов категорий, по которым нужно фильтровать события. Если null, фильтр не применяется.
     * @param rangeStart Начальная дата диапазона для фильтрации событий по времени. Если null, фильтр не применяется.
     * @param rangeEnd   Конечная дата диапазона для фильтрации событий по времени. Если null, фильтр не применяется.
     * @param from       Начальная позиция списка событий для пагинации.
     * @param size       Количество событий в списке для пагинации.
     * @return Список событий, соответствующих заданным критериям, в формате DTO.
     */
    List<EventDto> getAllEvents(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart,
                                String rangeEnd, Integer from, Integer size);

    /**
     * Метод обновляет информацию о событии в системе.
     *
     * @param eventId                 идентификатор события, которое нужно обновить
     * @param eventUpdateAdminRequest объект, содержащий новые данные для обновления события
     * @return обновлённый объект EventDto
     */
    EventDto updateEvent(Integer eventId, EventUpdateAdminRequest eventUpdateAdminRequest);

    /**
     * Метод возвращает список сокращённых данных о публичных событиях, соответствующих заданным фильтрам.
     *
     * @param httpRequest   объект HttpServletRequest, содержащий информацию о HTTP-запросе
     * @param text          строка для поиска по названию и описанию события
     * @param categories    список идентификаторов категорий, к которым должны принадлежать события
     * @param paid          флаг, указывающий на то, должны ли быть включены платные события (null — не имеет значения)
     * @param rangeStart    начало временного диапазона для поиска событий в формате YYYY-MM-DD (null — не имеет значения)
     * @param rangeEnd      конец временного диапазона для поиска событий в формате YYYY-MM-DD (null — не имеет значения)
     * @param onlyAvailable флаг, указывающий на то, должны ли быть включены только доступные события (null — не имеет значения)
     * @param sort          объект Sort, определяющий порядок сортировки событий
     * @param from          индекс первого элемента, который нужно включить в результат
     * @param size          количество элементов, которые нужно включить в результат
     * @return список объектов EventShortDto, соответствующих заданным фильтрам
     */
    List<EventShortDto> getPublicEvents(HttpServletRequest httpRequest, String text, List<Integer> categories,
                                        Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                        Sort sort, Integer from, Integer size);

    /**
     * Метод возвращает детальные данные о событии по его идентификатору.
     *
     * @param id          идентификатор события, для которого нужно получить данные
     * @param httpRequest объект HttpServletRequest, содержащий информацию о HTTP-запросе
     * @return объект EventDto, содержащий детальные данные о событии
     */
    EventDto getEventById(Integer id, HttpServletRequest httpRequest);

    /**
     * Метод возвращает список сокращённых данных о событиях, связанных с указанным пользователем.
     *
     * @param userId идентификатор пользователя, чьи события нужно получить
     * @param from   индекс первого элемента, который нужно включить в результат
     * @param size   количество элементов, которые нужно включить в результат
     * @return список объектов EventShortDto, содержащих сокращённые данные о событиях пользователя
     */
    List<EventShortDto> getUsersEvents(Integer userId, Integer from, Integer size);

    /**
     * Метод создаёт новое событие для указанного пользователя.
     *
     * @param userId идентификатор пользователя, который создаёт событие
     * @param event  объект NewEventDto, содержащий данные для создания события
     * @return объект EventDto, содержащий данные о созданном событии
     */
    EventDto createEvent(Integer userId, NewEventDto event);

    /**
     * Метод возвращает полную информацию о событии по его идентификатору для указанного пользователя.
     *
     * @param userId  идентификатор пользователя, запрашивающего информацию о событии
     * @param eventId идентификатор события, для которого нужно получить полную информацию
     * @return объект EventDto, содержащий полную информацию о событии
     */
    EventDto getFullInformation(Integer userId, Integer eventId);

    /**
     * Метод обновляет событие пользователя по его идентификатору.
     *
     * @param userId  идентификатор пользователя, который обновляет событие
     * @param eventId идентификатор события, которое нужно обновить
     * @param event   объект EventUpdateUserRequest, содержащий новые данные для обновления события
     * @return объект EventDto, содержащий обновлённые данные о событии
     */
    EventDto updateUsersEvent(Integer userId, Integer eventId, EventUpdateUserRequest event);

    /**
     * Метод возвращает список запросов, связанных с указанным событием и пользователем.
     *
     * @param userId  идентификатор пользователя, чьи запросы нужно получить
     * @param eventId идентификатор события, для которого нужно получить запросы
     * @return список объектов RequestDto, содержащих информацию о запросах
     */
    List<RequestDto> getInfoAboutRequests(Integer userId, Integer eventId);

    /**
     * Метод изменяет статус запроса на участие в событии.
     *
     * @param userId  идентификатор пользователя, который изменяет статус запроса
     * @param eventId идентификатор события, для которого изменяется статус запроса
     * @param request объект EventStatusUpdateRequest, содержащий новый статус запроса
     * @return объект EventStatusUpdateResult, содержащий результат изменения статуса запроса
     */
    EventStatusUpdateResult changeRequestStatus(Integer userId, Integer eventId,
                                                EventStatusUpdateRequest request);
}
