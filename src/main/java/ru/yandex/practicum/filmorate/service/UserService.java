package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

import ru.yandex.practicum.filmorate.model.User;

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
