package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.CommentShortDto;

import java.util.List;

/**
 * Сервис для работы с комментариями.
 * Определяет методы для выполнения бизнес-логики, связанной с комментариями.
 */
public interface CommentService {
    /**
     * Создаёт новый комментарий к событию.
     *
     * @param userId     идентификатор пользователя, который оставляет комментарий
     * @param eventId    идентификатор события, к которому добавляется комментарий
     * @param commentDto объект с данными комментария
     * @return сокращённый DTO объекта комментария
     */
    CommentShortDto createComment(Integer userId, Integer eventId, CommentInputDto commentDto);

    /**
     * Обновляет комментарий.
     *
     * @param userId     идентификатор пользователя, который обновляет комментарий
     * @param commentDto объект с новыми данными комментария
     * @param commentId  идентификатор комментария, который нужно обновить
     * @return сокращённый DTO объекта обновлённого комментария
     */
    CommentShortDto updateComment(Integer userId, CommentInputDto commentDto, Integer commentId);

    /**
     * Удаляет комментарий.
     *
     * @param userId    идентификатор пользователя, который удаляет комментарий
     * @param commentId идентификатор комментария, который нужно удалить
     */
    void deleteComment(Integer userId, Integer commentId);

    /**
     * Удаление комментария по идентификатору (функционал администратора)
     *
     * @param commentId идентификатор комментария, который нужно удалить
     */
    void deleteComment(Integer commentId);

    /**
     * Возвращает список всех комментариев к указанному событию.
     *
     * @param eventId идентификатор события, для которого нужно получить комментарии
     * @return список сокращённых DTO объектов комментариев
     */
    List<CommentShortDto> getAllCommentsByEventId(Integer eventId);
}
