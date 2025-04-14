package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.dto.State;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.util.List;

/**
 * Репозиторий для работы с сущностью Event.
 * Предоставляет стандартные методы CRUD (Create, Read, Update, Delete) для работы с подборками в базе данных.
 */
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByInitiator(User user);

    /**
     * Метод для получения списка событий с применением фильтров.
     * Используется для поиска событий по заданным критериям.
     * <p>
     *
     * @param users      Список идентификаторов пользователей, чьи события нужно включить в результат. Если null, фильтр не применяется.
     * @param states     Список состояний событий, по которым нужно фильтровать. Если null, фильтр не применяется.
     * @param categories Список идентификаторов категорий, по которым нужно фильтровать события. Если null, фильтр не применяется.
     * @param rangeStart Начальная дата диапазона для фильтрации событий по времени. Если null, фильтр не применяется.
     * @param rangeEnd   Конечная дата диапазона для фильтрации событий по времени. Если null, фильтр не применяется.
     * @return Список событий, соответствующих заданным критериям.
     */
    @Query("SELECT event FROM Event event " +
            "WHERE (:users IS NULL OR event.initiator.id IN :users) " +
            "AND (:states IS NULL OR event.state IN :states) " +
            "AND (:categories IS NULL OR event.category.id IN :categories) " +
            "AND (:rangeStart IS NULL OR event.eventDate >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR event.eventDate <= :rangeEnd)")
    List<Event> getEventsWithFilter(List<Integer> users, List<String> states, List<Integer> categories,
                                    String rangeStart, String rangeEnd);

    /**
     * Метод для получения списка публичных событий с применением фильтров.
     * Используется для поиска событий, которые находятся в состоянии PUBLISHED, с возможностью фильтрации по тексту, платности и временному диапазону.
     * <p>
     *
     * @param state      Состояние события (должно быть PUBLISHED для публичных событий).
     * @param text       Текст для поиска в аннотации и описании события. Если null, фильтр не применяется.
     * @param paid       Флаг, указывающий, является ли событие платным. Если null, фильтр не применяется.
     * @param rangeStart Начальная дата диапазона для фильтрации событий по времени. Если null, фильтр не применяется.
     * @param rangeEnd   Конечная дата диапазона для фильтрации событий по времени. Если null, фильтр не применяется.
     * @return Список публичных событий, соответствующих заданным критериям.
     */
    @Query("SELECT event FROM Event event " +
            "WHERE event.state= :state " +
            "AND (:text IS NULL OR (LOWER(event.description) LIKE %:text% OR LOWER(event.annotation) LIKE %:text%)) " +
            "AND (:paid IS NULL OR event.paid = :paid) " +
            "AND (:rangeStart IS NULL OR event.eventDate >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR event.eventDate <= :rangeEnd) " +
            "ORDER BY  event.eventDate")
    List<Event> getPublicEventsWithFilter(State state, String text, Boolean paid, String rangeStart,
                                          String rangeEnd);
}
