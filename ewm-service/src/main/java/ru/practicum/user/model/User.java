package ru.practicum.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность, представляющая пользователя.
 * Используется для хранения данных о пользователе в базе данных.
 * <p>
 * Поля:
 * - id: Уникальный идентификатор пользователя. Генерируется автоматически.
 * - email: Электронная почта пользователя. Не может быть null и должна быть уникальной.
 * - name: Имя пользователя.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String email;
    private String name;
}
