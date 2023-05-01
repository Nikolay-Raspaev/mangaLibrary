package com.LabWork.app.MangaStore.model.Dto;

import com.LabWork.app.MangaStore.model.Default.User;
import com.LabWork.app.MangaStore.model.Default.UserRole;

public class UserDto {
    private final long id;
    private final String login;
    private final UserRole role;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.role = user.getRole();
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }
}
