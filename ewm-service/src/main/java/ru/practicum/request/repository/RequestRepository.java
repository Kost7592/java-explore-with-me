package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.util.List;

/**
 * Репозиторий для работы с запросами на участие в событии.
 */
public interface RequestRepository extends JpaRepository<Request, Integer> {
    /**
     * Метод поиска запросов определённого пользователя.
     *
     * @param user пользователь, чьи запросы необходимо найти
     * @return список запросов пользователя.
     */
    List<Request> findByUser(User user);
}
