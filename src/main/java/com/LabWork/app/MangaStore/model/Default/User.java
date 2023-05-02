package com.LabWork.app.MangaStore.model.Default;

import com.LabWork.app.MangaStore.model.Dto.UserDto;
import com.LabWork.app.MangaStore.model.Dto.UserSignupDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Login can't be null or empty")
    @Size(min = 3, max = 64, message = "Incorrect login length")
    private String login;
    @NotBlank(message = "Email can't be null or empty")
    @Size(min = 3, max = 64, message = "Incorrect login length")
    private String email;
    @NotBlank(message = "Password can't be null or empty")
    @Size(min = 3, max = 64, message = "Incorrect password length")
    private String password;
    private UserRole role;

    public User() {
    }

    public User(String login, String email, String password, UserRole role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(UserDto userDto) {
        this.login = userDto.getLogin();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.role = userDto.getRole();
    }

    public User(UserSignupDto userSignupDto) {
        this.login = userSignupDto.getLogin();
        this.email = userSignupDto.getEmail();
        this.password = userSignupDto.getPassword();
        this.role = UserRole.USER;
    }

    // Properties

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setRole(UserRole role) {
        this.role = role;
    }

    // ![Properties]

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User marketUser = (User) o;
        return Objects.equals(id, marketUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
