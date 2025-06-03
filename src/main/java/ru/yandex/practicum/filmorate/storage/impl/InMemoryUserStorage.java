package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

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
    public User create(User user) {
        return save(user);
    }

    @Override
    public User update(User user) {
        return save(user);
    }

    private User save(User user) {
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public boolean checkEmailUnique(String email) {
        return users.values().stream().noneMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean checkLoginUnique(String login) {
        return users.values().stream().noneMatch(user -> user.getLogin().equals(login));
    }

    @Override
    public Collection<User> findFriends(User user) {
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
    public void addFriend(User user, User friend) {
        if (!user.getFriendIds().contains(friend.getId())) {
            user.getFriendIds().add(friend.getId());
            friend.getFriendIds().add(user.getId());
        }
    }

    @Override
    public void removeFriend(User user, User friend) {
        if (user.getFriendIds().contains(friend.getId())) {
            user.getFriendIds().remove(friend.getId());
            friend.getFriendIds().remove(user.getId());
        }
    }

    @Override
    public void acceptFriendship(Long userId, Long friendId) {

    }

}
