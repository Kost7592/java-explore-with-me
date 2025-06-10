package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.dto.CommentShortDto;
import ru.practicum.comment.service.CommentService;

import java.util.List;

/**
 * Класс PublicCommentController публичный контроллер для управления комментариями.
 */
@RequestMapping(path = "/comments")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PublicCommentController {
    private final CommentService commentService;

    /**
     * Получение всех комментариев события по его идентификатору
     * @param eventId идентификатор события.
     * @return список сокращённых DTO объектов комментариев.
     */
    @GetMapping("/{eventId}")
    private List<CommentShortDto> getAllCommentsByEventId(@PathVariable Integer eventId) {
        return commentService.getAllCommentsByEventId(eventId);
    }
}