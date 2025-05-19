package ru.practicum.comment.mapper;

import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.CommentShortDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.event.model.Event;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

/**
 * Маппер для преобразования объектов, связанных с комментариями, между различными представлениями (DTO и сущностями).
 */
public class CommentMapper {

    /**
     * Преобразует сущность Compilation в CompilationDto.
     *
     * @param commentDto DTO с данными для создания нового комментария.
     * @param user       автор комментария.
     * @param event      комментируемое событие.
     * @return Сущность Comment, созданная на основе данных из DTO.
     */
    public static Comment toComment(CommentInputDto commentDto, User user, Event event) {
        return Comment.builder()
                .text(commentDto.getText())
                .author(user)
                .event(event)
                .created(LocalDateTime.now())
                .build();
    }

    /**
     * Преобразует сущность Comment в CommentShortDto.
     *
     * @param comment Сущность Comment, которую необходимо преобразовать.
     * @return CommentShortDto, содержащий данные из сущности Comment.
     */
    public static CommentShortDto toCommentShortDto(Comment comment) {
        return CommentShortDto.builder()
                .author(UserMapper.toUserShortDto(comment.getAuthor()))
                .text(comment.getText())
                .created(comment.getCreated())
                .build();
    }
}
