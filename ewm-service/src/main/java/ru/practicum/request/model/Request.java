package ru.practicum.request.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.event.model.Event;
import ru.practicum.request.dto.RequestStatus;
import ru.practicum.user.model.User;

import java.time.Instant;

/**
 * Сущность, представляющая запрос на участие в событии.
 * Используется для хранения данных о запросе в базе данных.
 * <p>
 * Поля:
 * - id: Уникальный идентификатор запроса. Генерируется автоматически.
 * - user: Пользователь, подавший запрос. Связь многие-к-одному с сущностью User.
 * - status: Текущий статус запроса (например, PENDING, CONFIRMED, REJECTED, CANCELED). Не может быть null.
 * - created: Дата и время создания запроса. Заполняется автоматически при создании.
 * - event: Событие, на которое подается запрос. Связь многие-к-одному с сущностью Event.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "requests")
@Builder
@Table(indexes = {@Index(name = "multi_index", columnList = "requester_id,event_id", unique = true)})
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RequestStatus status;
    @CreationTimestamp
    private Instant created;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;
}
