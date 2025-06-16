package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.time.LocalDate;
import java.util.Collection;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class, UserRowMapper.class})
class UserDbStorageTest {

    private final UserDbStorage userStorage;

    private final User user1 = User.builder()
            .name("Test1")
            .email("test@test.ru")
            .login("test1")
            .birthday(LocalDate.of(1990, 1, 1))
            .build();

    private final User user2 = User.builder()
            .name("Test2")
            .email("test2@test.ru")
            .login("test2")
            .birthday(LocalDate.of(1991, 1, 1))
            .build();

    @Test
    public void testFindAll() {
        userStorage.create(user1);
        userStorage.create(user2);

        Collection<User> users = userStorage.findAll();
        Assertions.assertEquals(2, users.size());
    }

    @Test
    public void testFindById() {
        User created = userStorage.create(user1);
        User user = userStorage.findById(created.getId());

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user1.getEmail(), user.getEmail());
    }

    @Test
    public void testCreate() {
        User user = userStorage.create(user1);
        Assertions.assertNotNull(user);

        Collection<User> users = userStorage.findAll();
        Assertions.assertEquals(1, users.size());
    }

    @Test
    public void testUpdate() {
        User created = userStorage.create(user1);
        created.setName("TestUpdated");

        userStorage.update(created);
        User updated = userStorage.findById(created.getId());

        Assertions.assertNotNull(updated);
        Assertions.assertEquals("TestUpdated", updated.getName());
    }

    @Test
    public void testAddFriendUser() {
        User friend1 = userStorage.create(user1);
        User friend2 = userStorage.create(user2);

        userStorage.addFriend(friend1, friend2);
        Collection<User> friends = userStorage.findFriends(friend1);
        Assertions.assertEquals(1, friends.size());
    }

    @Test
    public void testRemoveFriendUser() {
        User friend1 = userStorage.create(user1);
        User friend2 = userStorage.create(user2);

        userStorage.addFriend(friend1, friend2);
        Collection<User> friends = userStorage.findFriends(friend1);
        Assertions.assertEquals(1, friends.size());

        userStorage.removeFriend(friend1, friend2);
        Collection<User> friend1RemovedFriends = userStorage.findFriends(friend1);
        Assertions.assertEquals(0, friend1RemovedFriends.size());
    }
}