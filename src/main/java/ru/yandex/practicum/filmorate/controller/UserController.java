package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> list() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody @Valid User userRequest) {
        if (null == userRequest.getName() || userRequest.getName().isBlank()) {
            userRequest.setName(userRequest.getLogin());
        }

        Long id = generateId();
        userRequest.setId(id);

        users.put(id, userRequest);
        log.info("User successfully created");

        return userRequest;
    }

    @PutMapping
    public User update(@RequestBody @Valid User userRequest) {
        if (null == userRequest.getId()) {
            throw new ValidationException("Id should be specified");
        }
        if (!users.containsKey(userRequest.getId())) {
            throw new ValidationException(String.format("User with id=%d not found", userRequest.getId()));
        }
        if (null == userRequest.getName() || userRequest.getName().isBlank()) {
            userRequest.setName(userRequest.getLogin());
        }

        User updateUser = users.get(userRequest.getId());
        if (!updateUser.getEmail().equals(userRequest.getEmail())) {
            checkEmailUnique(userRequest.getEmail());
        }
        if (!updateUser.getLogin().equals(userRequest.getLogin())) {
            checkLoginUnique(userRequest.getLogin());
        }

        users.put(userRequest.getId(), userRequest);
        log.info("User successfully updated");

        return userRequest;
    }

    private void checkEmailUnique(String email) {
        if (users.values()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email))) {
            throw new ValidationException(String.format("Email %s already in use", email));
        }
    }

    private void checkLoginUnique(String login) {
        if (users.values()
                .stream()
                .anyMatch(user -> user.getLogin().equals(login))) {
            throw new ValidationException(String.format("Login %s already in use", login));
        }
    }

    private long generateId() {
        long maxId = users.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++maxId;
    }

}
