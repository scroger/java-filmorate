package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

    Collection<User> findAll();

    User findById(Long id);

    void save(User user);

    void checkEmailUnique(String email);

    void checkLoginUnique(String login);

    Collection<User> findFriends(Long id);

    Collection<User> findCommonFriends(Long userId, Long otherId);

    void addFriend(Long id, Long friendId);

    void removeFriend(Long id, Long friendId);

}
