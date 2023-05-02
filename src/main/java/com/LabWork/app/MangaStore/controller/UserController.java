package com.LabWork.app.MangaStore.controller;


import com.LabWork.app.MangaStore.configuration.OpenAPI30Configuration;
import com.LabWork.app.MangaStore.model.Default.User;
import com.LabWork.app.MangaStore.model.Default.UserRole;
import com.LabWork.app.MangaStore.model.Dto.UserDto;
import com.LabWork.app.MangaStore.model.Dto.UserSignupDto;
import com.LabWork.app.MangaStore.service.UserService;
import com.LabWork.app.MangaStore.util.validation.ValidationException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
public class UserController {
    public static final String URL_LOGIN = "/jwt/login";
    public static final String URL_SING_UP = "/sing_up";
    public static final String URL_WHO_AM_I = "/who_am_i";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(URL_LOGIN)
    public String login(@RequestBody @Valid UserDto userDto) {
        return userService.loginAndGetToken(userDto);
    }

    @PostMapping(URL_SING_UP)
    public String singUp(@RequestBody @Valid UserSignupDto userSignupDto) {
        try {
            final User user = userService.addUser(userSignupDto.getLogin(), userSignupDto.getEmail(),
                    userSignupDto.getPassword(), userSignupDto.getPasswordConfirm(), UserRole.USER);
            return "created " + user.getLogin();
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }

    @GetMapping(OpenAPI30Configuration.API_PREFIX + "/user")
    public UserDto getUser(@RequestParam("login") String login) {
        User user = userService.findByLogin(login);
        return new UserDto(user);
    }

    @PostMapping(OpenAPI30Configuration.API_PREFIX + "/user")
    public String updateUser(@RequestBody @Valid UserDto userDto) {
        try {
            userService.updateUser(userDto);
            return "Profile updated";
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }

    @DeleteMapping(OpenAPI30Configuration.API_PREFIX + "/user/{id}")
    @Secured({UserRole.AsString.USER})
    public UserDto removeUser(@PathVariable Long id) {
        User user = userService.deleteUser(id);
        return new UserDto(user);
    }

    @GetMapping(OpenAPI30Configuration.API_PREFIX + "/users")
    @Secured({UserRole.AsString.ADMIN})
    public Pair<Page<UserDto>, List<Integer>> getUsers(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size) {
        final Page<UserDto> users = userService.findAllPages(page, size)
                .map(UserDto::new);
        final int totalPages = users.getTotalPages();
        final List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .toList();
        return new Pair<>(users, pageNumbers);
    }

    @GetMapping(URL_WHO_AM_I)
    public String whoAmI(@RequestParam("token") String token) {
        UserDetails userDetails = userService.loadUserByToken(token);
        User user = userService.findByLogin(userDetails.getUsername());
        return user.getRole().toString();
    }
}
