package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.CommentShortDto;
import ru.practicum.comment.service.CommentService;

/**
 * Класс PublicCommentController приватный контроллер для управления комментариями.
 */
@RequestMapping("/users/{userId}/comments")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentController {
    private final CommentService commentService;

    /**
     * Создаёт новый комментарий к событию.
     *
     * @param userId     идентификатор пользователя, который оставляет комментарий
     * @param eventId    идентификатор события, к которому добавляется комментарий
     * @param commentDto объект с данными комментария
     * @return сокращённый DTO объекта комментария
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentShortDto createComment(@PathVariable Integer userId,
                                         @RequestParam Integer eventId,
                                         @RequestBody CommentInputDto commentDto) {
        log.info("Получен запрос на создание комментария {}", commentDto);
        return commentService.createComment(userId, eventId, commentDto);
    }

    /**
     * Обновляет существующий комментарий.
     *
     * @param userId     идентификатор пользователя, который обновляет комментарий
     * @param commentDto объект с новыми данными комментария
     * @param commentId  идентификатор комментария, который нужно обновить
     * @return сокращённый DTO объекта обновлённого комментария
     */
    @PatchMapping("/{commentId}")
    public CommentShortDto updateComment(@PathVariable Integer userId,
                                         @RequestBody CommentInputDto commentDto,
                                         @PathVariable Integer commentId) {
        log.info("Получен запрос на редактирование комментария {}", commentDto);
        return commentService.updateComment(userId, commentDto, commentId);
    }

    /**
     * Удаление комментария пользователем.
     *
     * @param userId    идентификатор пользователя, который удаляет комментарий
     * @param commentId идентификатор комментария, который нужно удалить
     */
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer userId,
                              @PathVariable Integer commentId) {
        log.info("Получен запрос на удаление комментария c id:{}", commentId);
        commentService.deleteComment(userId, commentId);
    }
}
