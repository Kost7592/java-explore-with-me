package ru.practicum.event.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.State;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.util.List;

/**
 * Сущность, представляющая событие.
 * Используется для хранения данных о событии в базе данных.
 * <p>
 * Поля:
 * - id: Уникальный идентификатор события. Генерируется автоматически.
 * - annotation: Краткое описание события. Не может быть null, максимальная длина 2000 символов.
 * - category: Категория события. Связь многие-к-одному с сущностью Category.
 * - createdOn: Дата и время создания события.
 * - description: Полное описание события. Максимальная длина 7000 символов.
 * - eventDate: Дата и время проведения события.
 * - initiator: Пользователь, создавший событие. Связь многие-к-одному с сущностью User.
 * - lon: Долгота местоположения события.
 * - lat: Широта местоположения события.
 * - paid: Флаг, указывающий, является ли событие платным.
 * - participantLimit: Ограничение на количество участников события.
 * - publishedOn: Дата и время публикации события.
 * - requestModeration: Флаг, указывающий, требуется ли модерация запросов на участие.
 * - state: Текущее состояние события (например, PENDING, PUBLISHED, CANCELED). По умолчанию PENDING.
 * - title: Заголовок события.
 * - views: Количество просмотров события.
 * - requests: Список запросов на участие в событии. Связь один-ко-многим с сущностью Request.
 */
@Data
@ToString(exclude = "requests")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "annotation", nullable = false, length = 2000)
    private String annotation;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    private String createdOn;
    @Column(name = "description", length = 7000)
    private String description;
    private String eventDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User initiator;
    private float lon;
    private float lat;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    private String publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;
    private String title;
    private Integer views;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch = FetchType.EAGER)
    private List<Request> requests;
}