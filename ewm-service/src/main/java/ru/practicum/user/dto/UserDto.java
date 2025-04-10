package ru.practicum.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) - объект для передачи данных о пользователе.
 * <p>
 * Поля:
 * - id идентификатор пользователя.
 * - email Электронная почта пользователя. Должна быть валидной и содержать от 6 до 254 символов.
 * - name Имя пользователя. Не может быть пустым и должно содержать от 2 до 250 символов.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
}
