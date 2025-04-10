package ru.practicum.compilation.mapper;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.mapper.EventMapper;

import static java.util.Objects.isNull;

/**
 * Маппер для преобразования объектов, связанных с подборками, между различными представлениями (DTO и сущностями).
 */
public class CompilationMapper {
    /**
     * Преобразует сущность Compilation в CompilationDto.
     *
     * @param compilation Сущность Compilation, которую необходимо преобразовать.
     * @return CompilationDto, содержащий данные из сущности Compilation.
     */
    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(!isNull(compilation.getEvents()) ? compilation.getEvents().stream()
                        .map(EventMapper::toEventShortDto)
                        .toList() : null)
                .title(compilation.getTitle())
                .pinned(!isNull(compilation.getPinned()) && compilation.getPinned())
                .build();
    }

    /**
     * Преобразует NewCompilationDto в сущность Compilation.
     *
     * @param newCompilationDto DTO с данными для создания новой подборки.
     * @return Сущность Compilation, созданная на основе данных из DTO.
     */
    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .build();
    }
}