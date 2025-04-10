package ru.practicum.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO (Data Transfer Object) для обновления данных подборки.
 * Используется для передачи данных, необходимых для обновления подборки, между слоями приложения.
 * <p>
 * Поля:
 * - id: Идентификатор подборки, которую необходимо обновить.
 * - events: Множество идентификаторов событий, которые будут включены в подборку.
 * - pinned: Флаг, указывающий, будет ли подборка закреплена.
 * - title: Заголовок подборки. Может быть пустым, но не должен превышать 50 символов.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCompilationDto {
    private Integer id;
    private Set<Integer> events;
    private Boolean pinned;
    @Size(max = 50)
    private String title;
}

