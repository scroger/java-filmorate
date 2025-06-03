package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    Collection<User> findAll();

    User findById(Long id);

    User create(User user);

    User update(User user);

    boolean checkEmailUnique(String email);

    boolean checkLoginUnique(String login);

    Collection<User> findFriends(User user);

    Collection<User> findCommonFriends(Long userId, Long otherId);

    void addFriend(User user, User friend);

    void removeFriend(User user, User friend);

    void acceptFriendship(Long userId, Long friendId);

}
