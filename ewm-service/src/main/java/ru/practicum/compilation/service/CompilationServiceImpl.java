package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.error.exception.CompilationNotFoundException;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * Реализация сервиса для работы с подборками.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(compilationDto);
        if (!isNull(compilationDto.getEvents())) {
            List<Event> events = eventRepository.findAllById(compilationDto.getEvents());
            compilation.setEvents(events);
        }
        Compilation result = compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(result);
    }

    @Transactional
    @Override
    public CompilationDto updateCompilation(UpdateCompilationDto updateCompilationDto, Integer compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        if (!isNull(updateCompilationDto.getEvents())) {
            compilation.setEvents(eventRepository.findAllById(updateCompilationDto.getEvents()));
        }
        compilation.setPinned(updateCompilationDto.getPinned());
        compilation.setTitle(updateCompilationDto.getTitle());
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Transactional
    @Override
    public void deleteCompilation(Integer compId) {
        boolean exists = compilationRepository.existsById(compId);
        if (!exists) {
            throw new CompilationNotFoundException();
        }
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto getCompilationById(Integer compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getAllEvents(Boolean pinned, Integer from, Integer size) {
        List<Compilation> all = compilationRepository.findAll();
        List<CompilationDto> result = all.stream()
                .filter(compilation -> {
                    if (isNull(pinned)) {
                        return true;
                    } else if (isNull(compilation.getPinned())) {
                        return false;
                    }
                    return compilation.getPinned().equals(pinned);
                })
                .skip(from)
                .limit(size)
                .map(CompilationMapper::toCompilationDto)
                .toList();
        log.info("Найдены все компиляции размером {}", all.size());
        log.info("Отфильтрована компиляция {}", result.size());
        return result;
    }
}
