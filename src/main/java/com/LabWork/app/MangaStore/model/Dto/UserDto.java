package com.LabWork.app.MangaStore.model.Dto;


import com.LabWork.app.MangaStore.model.Default.User;
import com.LabWork.app.MangaStore.model.Default.UserRole;

public class UserDto {
    private Long id;
    private String login;
    private String email;
    private String password;
    private UserRole role;
    private Long creatortId;

    private Long readerId;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public Long getReaderId() {
        return readerId;
    }
    public Long getCreatorId() {
        return creatortId;
    }
}

