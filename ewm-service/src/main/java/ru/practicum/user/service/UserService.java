package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.dto.UserDto;

import java.util.List;

/**
 * Сервисный интерфейс для работы с пользователями.
 */
public interface UserService {
    /**
     * Метод для создания нового пользователя.
     *
     * @param userRequest новый пользователь в формате NewUserDto.
     * @return созданный пользователь в формате UserDto.
     */
    UserDto createUser(NewUserDto userRequest);

    /**
     * Возвращает список пользователей с возможностью фильтрации по идентификаторам и пагинацией.
     *
     * @param ids  Список идентификаторов пользователей, которых нужно включить в результат. Если null, возвращаются все пользователи.
     * @param from Начальная позиция списка пользователей для пагинации.
     * @param size Количество пользователей в списке для пагинации.
     * @return Список пользователей в формате DTO, соответствующих заданным критериям.
     */
    List<UserDto> getAllUsers(List<Integer> ids, Integer from, Integer size);

    /**
     * Удаляет пользователя.
     *
     * @param userId идентификатор удаляемого пользователя.
     */
    void deleteUser(Integer userId);
}
