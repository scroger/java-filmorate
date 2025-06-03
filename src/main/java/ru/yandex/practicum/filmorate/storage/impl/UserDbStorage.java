package ru.yandex.practicum.filmorate.storage.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@Primary
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final static String FIND_ALL_SQL = "SELECT * FROM users";
    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id=?";
    private final static String CREATE_SQL = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
    private final static String UPDATE_SQL = "UPDATE users SET email=?, login=?, name=?, birthday=? WHERE id=?";
    private final static String FIND_FRIENDS_SQL = """
            SELECT * FROM users WHERE id IN (SELECT friend_id FROM user_friends WHERE user_id=?)""";
    private final static String FIND_COMMON_FRIENDS_SQL = """
            SELECT * FROM users WHERE id IN (
                SELECT uf1.friend_id FROM user_friends uf1
                LEFT JOIN user_friends uf2 ON uf1.friend_id=uf2.friend_id
                WHERE uf1.user_id=? AND uf2.user_id=?
            )""";
    private final static String ADD_FRIEND_SQL = "INSERT INTO user_friends (user_id, friend_id, status) VALUES (?, ?, false)";
    private final static String REMOVE_FRIEND_SQL = "DELETE FROM user_friends WHERE user_id=? AND friend_id=?";
    private final static String ACCEPT_FRIEND_SQL = "UPDATE user_friends SET status=true WHERE user_id=? AND friend_id=?";


    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper;

    @Override
    public Collection<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, userRowMapper);
    }

    @Override
    public User findById(Long id) {
        Optional<User> optUser;
        try {
            optUser = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            optUser = Optional.empty();
        }

        if (optUser.isEmpty()) {
            throw new NotFoundException(String.format("User with id=%d not found.", id));
        }

        return optUser.get();
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));

            return stmt;
        }, keyHolder);

        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return user;
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update(
                UPDATE_SQL,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        return user;
    }

    @Override
    public boolean checkEmailUnique(String email) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(id) FROM users WHERE email=?",
                Integer.class,
                email
        );

        return null == count || 0 == count;
    }

    @Override
    public boolean checkLoginUnique(String login) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(id) FROM users WHERE login=?",
                Integer.class,
                login
        );

        return null == count || 0 == count;
    }

    @Override
    public Collection<User> findFriends(Long userId) {
        return jdbcTemplate.query(FIND_FRIENDS_SQL, userRowMapper, userId);
    }

    @Override
    public Collection<User> findCommonFriends(Long userId, Long otherId) {
        return jdbcTemplate.query(FIND_COMMON_FRIENDS_SQL, userRowMapper, userId, otherId);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        jdbcTemplate.update(
                ADD_FRIEND_SQL,
                userId,
                friendId
        );
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        jdbcTemplate.update(REMOVE_FRIEND_SQL, userId, friendId);
    }

    @Override
    public void acceptFriendship(Long userId, Long friendId) {
        jdbcTemplate.update(
                ACCEPT_FRIEND_SQL,
                userId,
                friendId
        );
    }
}
