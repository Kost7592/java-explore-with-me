package ru.practicum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Этот класс представляет собой сущность, которая используется для хранения информации о запросах к эндпоинтам.
 * Он содержит следующие поля:
 * — id — уникальный идентификатор записи;
 * — app — идентификатор сервиса для которого записывается информация;
 * — uri — URI для которого был осуществлен запрос;
 * — ip — IP-адрес пользователя, осуществившего запрос;
 * — timestamp — Дата и время, когда был совершен запрос к эндпоинту.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statistics")
@Builder
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String app;
    @Column(nullable = false)
    private String uri;
    @Column(nullable = false)
    private String ip;
    @Column(nullable = false, name = "creation date")
    private LocalDateTime timestamp;
}
