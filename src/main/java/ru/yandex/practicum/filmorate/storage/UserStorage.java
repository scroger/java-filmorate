package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

    Collection<User> findAll();

    Optional<User> findById(Long id);

    void save(User user);

    User create(User user);

    User update(User user);

    void checkEmailUnique(String email);

    void checkLoginUnique(String login);

    Collection<User> findFriends(Long id);

    Collection<User> findCommonFriends(Long userId, Long otherId);

//    void addFriend(Long id, Long friendId);

//    void removeFriend(Long id, Long friendId);

    void addFriend(User user, User friend, boolean status);

    void removeFriend(User user, User friend);

    void acceptFriendship(User user, User friend, boolean status);

}
