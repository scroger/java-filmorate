package ru.yandex.practicum.filmorate.storage.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(Long id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d not found.", id)));
    }

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void checkEmailUnique(String email) {
        if (users.values().stream().anyMatch(user -> user.getEmail().equals(email))) {
            throw new ValidationException(String.format("Email %s already in use", email));
        }
    }

    @Override
    public void checkLoginUnique(String login) {
        if (users.values().stream().anyMatch(user -> user.getLogin().equals(login))) {
            throw new ValidationException(String.format("Login %s already in use", login));
        }
    }

    @Override
    public Collection<User> findFriends(Long id) {
        final User user = findById(id);

        return users.values()
                .stream()
                .filter(u -> user.getFriendIds().contains(u.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<User> findCommonFriends(Long userId, Long otherId) {
        final User user1 = findById(userId);
        final User user2 = findById(otherId);
        final Set<Long> commonFriendIds = user1.getFriendIds()
                .stream()
                .filter(friendId -> user2.getFriendIds().contains(friendId))
                .collect(Collectors.toSet());

        return users.values()
                .stream()
                .filter(u -> commonFriendIds.contains(u.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        final User user1 = findById(id);

        if (!user1.getFriendIds().contains(friendId)) {
            final User user2 = findById(friendId);

            user1.getFriendIds().add(user2.getId());
            user2.getFriendIds().add(user1.getId());
        }
    }

    @Override
    public void removeFriend(Long id, Long friendId) {
        final User user1 = findById(id);

        if (user1.getFriendIds().contains(friendId)) {
            final User user2 = findById(friendId);

            user1.getFriendIds().remove(user2.getId());
            user2.getFriendIds().remove(user1.getId());
        }
    }

}
