package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

class UserControllerTest {

    private final UserController controller = new UserController();

    @Test
    void testCreateUserWithoutName() {
        User userRequest = new User();
        userRequest.setEmail("valid@mail.ru");
        userRequest.setLogin("login");

        User createdUser = Assertions.assertDoesNotThrow(() -> controller.create(userRequest));
        Assertions.assertEquals(userRequest.getLogin(), createdUser.getName());
    }

    @Test
    void testUpdateUserWithoutId() {
        User userRequest = new User();
        userRequest.setEmail("valid@mail.ru");
        userRequest.setLogin("login");

        Assertions.assertThrows(
                ValidationException.class,
                () -> controller.update(userRequest),
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
                ValidationException.class,
                () -> controller.update(userRequest),
                "User with id=1 not found"
        );
    }

    @Test
    void testUpdateUserChangeEmailSuccess() {
        User userRequest = new User();
        userRequest.setEmail("valid@mail.ru");
        userRequest.setLogin("login");

        User createdUser = controller.create(userRequest);
        Assertions.assertEquals(1L, createdUser.getId());
        Assertions.assertEquals(userRequest.getEmail(), createdUser.getEmail());

        User updatedUser = Assertions.assertDoesNotThrow(() -> controller.update(new User(
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
        controller.create(User.builder()
                .email("login1@mail.ru")
                .login("login1")
                .build());

        controller.create(User.builder()
                .email("login2@mail.ru")
                .login("login2")
                .build());

        Assertions.assertThrows(
                ValidationException.class,
                () -> controller.update(User.builder()
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

        User createdUser = controller.create(userRequest);
        Assertions.assertEquals(1L, createdUser.getId());
        Assertions.assertEquals(userRequest.getLogin(), createdUser.getLogin());

        User updatedUser = Assertions.assertDoesNotThrow(() -> controller.update(new User(
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
        controller.create(User.builder()
                .email("login1@mail.ru")
                .login("login1")
                .build());

        controller.create(User.builder()
                .email("login2@mail.ru")
                .login("login2")
                .build());

        Assertions.assertThrows(
                ValidationException.class,
                () -> controller.update(User.builder()
                        .email("login2@mail.ru")
                        .login("login1")
                        .build()),
                "Login login1 already in use"
        );
    }
}