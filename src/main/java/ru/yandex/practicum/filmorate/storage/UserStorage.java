package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

    Collection<User> findAll();

    User findById(Long id);

    User create(User user);

    User update(User user);

    boolean checkEmailUnique(String email);

    boolean checkLoginUnique(String login);

    Collection<User> findFriends(Long id);

    Collection<User> findCommonFriends(Long userId, Long otherId);

    void addFriend(Long id, Long friendId);

    void removeFriend(Long id, Long friendId);

    void acceptFriendship(Long userId, Long friendId);

}
