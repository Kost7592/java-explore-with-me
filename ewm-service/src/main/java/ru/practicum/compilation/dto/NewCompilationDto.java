package ru.practicum.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO (Data Transfer Object) для создания новой подборки.
 * Используется для передачи данных, необходимых для создания подборки, между слоями приложения.
 * <p>
 * Поля:
 * - events: Множество идентификаторов событий, которые будут включены в подборку.
 * - pinned: Флаг, указывающий, будет ли подборка закреплена.
 * - title: Заголовок подборки. Не может быть пустым и должен содержать от 1 до 50 символов.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {
    private Set<Integer> events;
    private Boolean pinned;
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
}

