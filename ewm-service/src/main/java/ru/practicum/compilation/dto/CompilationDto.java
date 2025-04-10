package ru.practicum.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

/**
 * DTO (Data Transfer Object) для представления данных о подборке.
 * Используется для передачи информации о подборке между слоями приложения.
 * <p>
 * Поля:
 * - id: Уникальный идентификатор подборки. Не может быть пустым.
 * - pinned: Флаг, указывающий, закреплена ли подборка.
 * - title: Заголовок подборки. Не может быть пустым и должен содержать не более 50 символов.
 * - events: Список краткой информации о событиях, входящих в подборку.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompilationDto {
    @NotEmpty
    private Integer id;
    private Boolean pinned;
    @Size(max = 50)
    @NotBlank
    private String title;
    private List<EventShortDto> events;
}
