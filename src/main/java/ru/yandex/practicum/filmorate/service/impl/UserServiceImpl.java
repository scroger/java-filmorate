package ru.yandex.practicum.filmorate.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User findById(Long id) {
        return userStorage.findById(id);
    }

    @Override
    public User create(User userRequest) {
        userStorage.checkEmailUnique(userRequest.getEmail());
        userStorage.checkLoginUnique(userRequest.getLogin());

        if (null == userRequest.getName() || userRequest.getName().isBlank()) {
            userRequest.setName(userRequest.getLogin());
        }
        userRequest.setId(generateId());

        userStorage.save(userRequest);
        log.info("User successfully created");

        return userRequest;
    }

    @Override
    public User update(User userRequest) {
        if (null == userRequest.getId()) {
            throw new ValidationException("Id should be specified");
        }

        User updateUser = userStorage.findById(userRequest.getId());
        if (!updateUser.getEmail().equals(userRequest.getEmail())) {
            userStorage.checkEmailUnique(userRequest.getEmail());
        }
        if (!updateUser.getLogin().equals(userRequest.getLogin())) {
            userStorage.checkLoginUnique(userRequest.getLogin());
        }

        if (null == userRequest.getName() || userRequest.getName().isBlank()) {
            userRequest.setName(userRequest.getLogin());
        }

        userStorage.save(userRequest);
        log.info("User successfully updated");

        return userRequest;
    }

    @Override
    public Collection<User> findFriends(Long id) {
        return userStorage.findFriends(id);
    }

    @Override
    public Collection<User> findCommonFriends(Long userId, Long otherId) {
        return userStorage.findCommonFriends(userId, otherId);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        userStorage.addFriend(id, friendId);

        log.info(String.format("Users with id=%d and id=%d are now friends.", id, friendId));
    }

    @Override
    public void removeFriend(Long id, Long friendId) {
        userStorage.removeFriend(id, friendId);

        log.info(String.format("Users with id=%d and id=%d are no longer friends.", id, friendId));
    }

    private long generateId() {
        long maxId = userStorage.findAll()
                .stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);

        return ++maxId;
    }

}
