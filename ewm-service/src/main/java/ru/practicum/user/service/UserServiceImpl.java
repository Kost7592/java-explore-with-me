package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.error.exception.UserNotFoundException;
import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * Реализация сервисного интерфейса для работы с пользователями.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers(List<Integer> ids, Integer from, Integer size) {
        if (isNull(ids)) {
            return userRepository.findAll().stream()
                    .skip(from)
                    .limit(size)
                    .map(UserMapper::toUserDto)
                    .toList();
        }
        return userRepository.findAllById(ids).stream()
                .skip(from)
                .limit(size)
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Transactional
    @Override
    public UserDto createUser(NewUserDto userRequest) {
        User user = UserMapper.toUser(userRequest);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Integer userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(userId);
    }
}
