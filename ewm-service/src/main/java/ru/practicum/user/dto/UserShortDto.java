package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) - объект для передачи данных о пользователе(урезанная версия).
 * <p>
 * Поля:
 * - id идентификатор пользователя.
 * - name Имя пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserShortDto {
    private Integer id;
    private String name;
}
