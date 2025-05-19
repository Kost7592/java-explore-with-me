package ru.practicum.comment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая комментарий.
 * Используется для хранения данных о комментариях в базе данных.
 * <p>
 * Поля:
 * - id: Уникальный идентификатор комментария. Генерируется автоматически.
 * - text: Текст комментария.
 * - event: Событие, к которому относится комментарий.
 * - author: Пользователь - автор комментария.
 * - created: дата и время создания комментария.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String text;
    @JoinColumn(name = "event_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}
