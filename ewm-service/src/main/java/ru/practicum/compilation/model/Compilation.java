package ru.practicum.compilation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import ru.practicum.event.model.Event;

import java.util.List;

/**
 * Сущность, представляющая подборку событий.
 * Используется для хранения данных о подборке в базе данных.
 * <p>
 * Поля:
 * - id: Уникальный идентификатор подборки. Генерируется автоматически.
 * - pinned: Флаг, указывающий, закреплена ли подборка. По умолчанию false.
 * - title: Заголовок подборки. Максимальная длина 50 символов.
 * - events: Список событий, входящих в подборку. Связь многие-ко-многим с сущностью Event.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "compilations")
@Builder
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ColumnDefault("false")
    private Boolean pinned;
    @Column(name = "title", length = 50)
    private String title;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "compilations_to_event", joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
}
