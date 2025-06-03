package ru.yandex.practicum.filmorate.storage.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final static String FIND_ALL_SQL = "SELECT * FROM users";
    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper;

    @Override
    public Collection<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, userRowMapper);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public User create(User user) {

    }

    @Override
    public User update(User user) {

    }

    @Override
    public void checkEmailUnique(String email) {

    }

    @Override
    public void checkLoginUnique(String login) {

    }

    @Override
    public Collection<User> findFriends(Long id) {
        return null;
    }

    @Override
    public Collection<User> findCommonFriends(Long userId, Long otherId) {
        return null;
    }

    @Override
    public void addFriend(Long id, Long friendId) {

    }

    @Override
    public void removeFriend(Long id, Long friendId) {

    }
}
