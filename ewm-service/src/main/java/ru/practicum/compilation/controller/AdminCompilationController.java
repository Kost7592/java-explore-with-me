package ru.practicum.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;
import ru.practicum.compilation.service.CompilationService;

/**
 * Контроллер для управления сборниками в административной панели.
 */
@RestController
@RequestMapping("/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;

    /**
     * Создает новую подборку на основе переданных данных.
     *
     * @param compilationDto DTO с данными для создания подборки. Должно быть валидным (аннотация @Valid).
     * @return Созданная подборка в формате DTO.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        log.info("Получен запрос на создание подборки, request: {}", compilationDto);
        CompilationDto compilation = compilationService.createCompilation(compilationDto);
        log.info("Получен ответ на создание подборки, response: {}", compilation);
        return compilation;
    }

    /**
     * Удаляет подборку по её идентификатору.
     *
     * @param compId Идентификатор подборки, которую необходимо удалить.
     */
    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Integer compId) {
        log.info("Получен запрос на удаление подборки. {}", compId);
        compilationService.deleteCompilation(compId);
    }

    /**
     * Обновляет данные подборки по её идентификатору.
     */
    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@RequestBody @Valid UpdateCompilationDto updateCompilationDto,
                                            @PathVariable Integer compId) {
        log.info("Получен запрос на обновление подборки request: {}, compId: {}.", updateCompilationDto, compId);
        CompilationDto compilationDto = compilationService.updateCompilation(updateCompilationDto, compId);
        log.info("Получен ответ на обновление подборки, response: {}", compilationDto);
        return compilationDto;
    }


}
