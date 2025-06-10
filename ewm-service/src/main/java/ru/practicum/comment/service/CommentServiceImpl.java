package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentInputDto;
import ru.practicum.comment.dto.CommentShortDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.error.exception.BadDataException;
import ru.practicum.error.exception.BadRequestDataException;
import ru.practicum.error.exception.UserNotFoundException;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * Реализация сервиса для работы с комментариями.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public CommentShortDto createComment(Integer userId, Integer eventId, CommentInputDto commentDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow();
        Comment comment = CommentMapper.toComment(commentDto, user, event);
        if (isNull(comment.getText())) {
            throw new BadRequestDataException("Нельзя оставлять пустой комментарий");
        }
        Comment result = commentRepository.save(comment);
        return CommentMapper.toCommentShortDto(result);
    }

    @Transactional
    @Override
    public CommentShortDto updateComment(Integer userId, CommentInputDto commentDto, Integer commentId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (user.getId().equals(comment.getAuthor().getId())) {
            comment.setText(commentDto.getText());
            if (comment.getText().isBlank()) {
                throw new BadDataException("Нельзя оставлять пустой комментарий");
            }
            Comment result = commentRepository.save(comment);
            return CommentMapper.toCommentShortDto(result);
        } else {
            throw new BadRequestDataException("Редактировать комментарий может только автор");
        }
    }

    @Transactional
    @Override
    public void deleteComment(Integer userId, Integer commentId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (user.getId().equals(comment.getAuthor().getId())) {
            commentRepository.delete(comment);
        } else {
            throw new BadRequestDataException("Удалить комментарий может только автор");
        }
    }

    @Transactional
    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentShortDto> getAllCommentsByEventId(Integer eventId) {
        List<Comment> comments = commentRepository.findAllByEventId(eventId);
        return comments.stream()
                .map(CommentMapper::toCommentShortDto)
                .toList();
    }
}
