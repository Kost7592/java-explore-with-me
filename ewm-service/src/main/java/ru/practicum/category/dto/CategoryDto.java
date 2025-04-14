package ru.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс CategoryDto - DTO-объект для передачи данных о категории.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;
    @NotBlank
    @Size(max = 50)
    private String name;
}
