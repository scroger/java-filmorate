package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {

    Collection<User> findAll();

    User findById(Long id);

    User create(User userRequest);

    User update(User userRequest);

    Collection<User> findFriends(Long id);

    Collection<User> findCommonFriends(Long userId, Long otherId);

    void addFriend(Long id, Long friendId);

    void removeFriend(Long id, Long friendId);

}
