package ru.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс NewCategoryDto - DTO-объект для передачи данных о новой категории.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCategoryDto {
    @NotBlank
    @Size(max = 50)
    private String name;
}

