package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.service.CommentService;

/**
 * Класс AdminCommentController административный контроллер для управления комментариями.
 */
@RequestMapping("/admin/comments")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminCommentController {
    private final CommentService commentService;

    /**
     * Удаление комментария администратором
     *
     * @param commentId идентификатор комментария, который нужно удалить
     */
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer commentId) {
        log.info("Получен запрос на удаление комментария c id:{}", commentId);
        commentService.deleteComment(commentId);
    }
}
