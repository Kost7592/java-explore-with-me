package ru.practicum.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) для представления данных о подборке.
 * Используется для передачи информации о подборке между слоями приложения.
 * <p>
 * Поля:
 * - text: текст комментария.
 * - author пользователь - автор комментария.
 * - created - дата и время создания комментария.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentShortDto {
    @NotBlank
    @Size(min = 5, max = 2000)
    private String text;
    private UserShortDto author;
    private LocalDateTime created;
}
