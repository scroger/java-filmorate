package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.UserServiceImpl;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

class UserControllerTest {

    private final UserController controller = new UserController(
            new UserServiceImpl(
                    new InMemoryUserStorage()
            )
    );

    @Test
    void testCreateUserWithoutName() {
        User userRequest = new User();
        userRequest.setEmail("valid@mail.ru");
        userRequest.setLogin("login");

        User createdUser = Assertions.assertDoesNotThrow(() -> controller.createUser(userRequest));
        Assertions.assertEquals(userRequest.getLogin(), createdUser.getName());
    }

    @Test
    void testUpdateUserWithoutId() {
        User userRequest = new User();
        userRequest.setEmail("valid@mail.ru");
        userRequest.setLogin("login");

        Assertions.assertThrows(
                ValidationException.class,
                () -> controller.updateUser(userRequest),
                "Id should be specified"
        );
    }

    @Test
    void testUpdateUserThatNotExist() {
        User userRequest = new User();
        userRequest.setId(1L);
        userRequest.setEmail("valid@mail.ru");
        userRequest.setLogin("login");

        Assertions.assertThrows(
                NotFoundException.class,
                () -> controller.updateUser(userRequest),
                "User with id=1 not found"
        );
    }

    @Test
    void testUpdateUserChangeEmailSuccess() {
        User userRequest = new User();
        userRequest.setEmail("valid@mail.ru");
        userRequest.setLogin("login");

        User createdUser = controller.createUser(userRequest);
        Assertions.assertEquals(1L, createdUser.getId());
        Assertions.assertEquals(userRequest.getEmail(), createdUser.getEmail());

        User updatedUser = Assertions.assertDoesNotThrow(() -> controller.updateUser(new User(
                createdUser.getId(),
                "login@mail.ru",
                createdUser.getLogin(),
                createdUser.getName(),
                createdUser.getBirthday()
        )));
        Assertions.assertEquals("login@mail.ru", updatedUser.getEmail());
    }

    @Test
    void testUpdateUserChangeEmailFailed() {
        controller.createUser(User.builder()
                .email("login1@mail.ru")
                .login("login1")
                .build());

        controller.createUser(User.builder()
                .email("login2@mail.ru")
                .login("login2")
                .build());

        Assertions.assertThrows(
                ValidationException.class,
                () -> controller.updateUser(User.builder()
                        .id(2L)
                        .email("login1@mail.ru")
                        .login("login2")
                        .build()),
                "Email login1@mail.ru already in use"
        );
    }

    @Test
    void testUpdateUserChangeLoginSuccess() {
        User userRequest = new User();
        userRequest.setEmail("valid@mail.ru");
        userRequest.setLogin("login");

        User createdUser = controller.createUser(userRequest);
        Assertions.assertEquals(1L, createdUser.getId());
        Assertions.assertEquals(userRequest.getLogin(), createdUser.getLogin());

        User updatedUser = Assertions.assertDoesNotThrow(() -> controller.updateUser(new User(
                createdUser.getId(),
                createdUser.getEmail(),
                "newlogin",
                createdUser.getName(),
                createdUser.getBirthday()
        )));
        Assertions.assertEquals("newlogin", updatedUser.getLogin());
    }

    @Test
    void testUpdateUserChangeLoginFailed() {
        controller.createUser(User.builder()
                .email("login1@mail.ru")
                .login("login1")
                .build());

        controller.createUser(User.builder()
                .email("login2@mail.ru")
                .login("login2")
                .build());

        Assertions.assertThrows(
                ValidationException.class,
                () -> controller.updateUser(User.builder()
                        .email("login2@mail.ru")
                        .login("login1")
                        .build()),
                "Login login1 already in use"
        );
    }
}