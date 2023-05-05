package com.LabWork.app;

import com.LabWork.app.MangaStore.model.Default.User;
import com.LabWork.app.MangaStore.model.Default.UserRole;
import com.LabWork.app.MangaStore.service.Exception.UserNotFoundException;
import com.LabWork.app.MangaStore.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JpaUserTests {
    private static final Logger log = LoggerFactory.getLogger(JpaUserTests.class);
    @Autowired
    private UserService userService;


    @Test
    void testUserCreate() {
        userService.deleteAllUsers();
        final User user = userService.addUser("User 1", "email@gmail.com",
                "123456", "123456", UserRole.USER);
        log.info("testUserCreate: " + user.toString());
        Assertions.assertNotNull(user.getId());

        userService.deleteAllUsers();
    }

    @Test
    void testUserRead() {
        userService.deleteAllUsers();
        final User user = userService.addUser("User 2", "email@gmail.com",
                "123456", "123456", UserRole.USER);
        log.info("testUserRead[0]: " + user.toString());
        final User findUser = userService.findUser(user.getId());
        log.info("testUserRead[1]: " + findUser.toString());
        Assertions.assertEquals(user, findUser);

        userService.deleteAllUsers();
    }

    @Test
    void testUserReadNotFound() {
        userService.deleteAllUsers();
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUser(-1L));
    }

    @Test
    void testUserReadAll() {
        userService.deleteAllUsers();
        userService.addUser("User 3", "email@gmail.com", "123456",
                "123456", UserRole.USER);
        userService.addUser("User 4", "email@gmail.com", "123456",
                "123456", UserRole.USER);
        final List<User> users = userService.findAllUsers();
        log.info("testUserReadAll: " + users.toString());
        Assertions.assertEquals(users.size(), 2);

        userService.deleteAllUsers();
    }

    @Test
    void testUserReadAllEmpty() {
        userService.deleteAllUsers();
        final List<User> users = userService.findAllUsers();
        log.info("testUserReadAllEmpty: " + users.toString());
        Assertions.assertEquals(users.size(), 0);
    }


}

