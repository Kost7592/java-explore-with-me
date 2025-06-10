package ru.practicum.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для представления данных о подборке.
 * Используется для передачи информации о подборке между слоями приложения.
 * <p>
 * Поля:
 * - text: текст комментария.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentInputDto {
    @Size(max = 2000)
    private String text;
}
