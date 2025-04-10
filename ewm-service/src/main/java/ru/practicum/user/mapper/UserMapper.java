package ru.practicum.user.mapper;

import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

/**
 * Класс для преобразования объектов User в DTO-объекты и обратно.
 */
public class UserMapper {
    /**
     * Преобразует класс User в UserShortDto
     *
     * @param user объект класса User
     * @return преобразованный объект UserShortDto
     */
    public static UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    /**
     * Преобразует класс NewUserDto в User.
     *
     * @param newUserDto объект класса NewUserDto.
     * @return преобразованный объект User.
     */
    public static User toUser(NewUserDto newUserDto) {
        return new User(
                null,
                newUserDto.getEmail(),
                newUserDto.getName());
    }

    /**
     * Преобразует класс User в UserDto.
     *
     * @param user объект класса User.
     * @return преобразованный объект UserDto.
     */
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
