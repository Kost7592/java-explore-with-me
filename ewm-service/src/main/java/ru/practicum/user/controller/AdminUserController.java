package ru.practicum.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import java.util.List;

/**
 * Контроллер для обработки административных запросов, связанных с пользователями.
 * Предоставляет API для управления пользователями (например, создание, удаление, получение списка пользователей).
 */
@RestController
@RequestMapping("/admin/users")
@Slf4j
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    /**
     * Возвращает список пользователей с возможностью фильтрации по идентификаторам и пагинацией.
     * <p>
     * Параметры:
     * - ids: Список идентификаторов пользователей, которых нужно включить в результат. Если null, возвращаются все пользователи.
     * - from: Начальная позиция списка пользователей для пагинации (по умолчанию 0).
     * - size: Количество пользователей в списке для пагинации (по умолчанию 10).
     */
    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(required = false) List<Integer> ids,
                                     @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                     @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {

        log.info("Получен запрос на получение всех пользователей");
        return userService.getAllUsers(ids, from, size);
    }

    /**
     * Метод создает нового пользователя.
     *
     * @param userRequest новый пользователь, которого нужно создать.
     * @return созданный пользователь UserDto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid NewUserDto userRequest) {
        log.info("Получен запрос на создание пользователя");
        return userService.createUser(userRequest);
    }

    /**
     * Метод для удаления пользователя
     *
     * @param userId идентификатор удаляемого пользователя
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer userId) {
        log.info("Получен запрос на удаление пользователя");
        userService.deleteUser(userId);
    }
}
