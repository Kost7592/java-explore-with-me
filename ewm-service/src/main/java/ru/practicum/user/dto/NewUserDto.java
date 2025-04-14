package ru.practicum.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для создания нового пользователя.
 * Используется для передачи данных, необходимых для создания пользователя.
 * <p>
 * Поля:
 * - email: Электронная почта пользователя. Должна быть валидной и содержать от 6 до 254 символов.
 * - name: Имя пользователя. Не может быть пустым и должно содержать от 2 до 250 символов.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserDto {
    @Email
    @NotBlank
    @Size(min = 6, max = 254)
    private String email;
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 250)
    private String name;
}
