package ru.practicum.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comment.model.Comment;

import java.util.List;

/**
 * Репозиторий для работы с сущностью Comment.
 * Предоставляет стандартные методы CRUD (Create, Read, Update, Delete) для работы с подборками в базе данных.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * Возвращает список всех комментариев, связанных с указанным событием.
     *
     * @param eventId идентификатор события, для которого нужно получить комментарии
     * @return список объектов Comment, соответствующих указанному событию
     */
    List<Comment> findAllByEventId(Integer eventId);
}
