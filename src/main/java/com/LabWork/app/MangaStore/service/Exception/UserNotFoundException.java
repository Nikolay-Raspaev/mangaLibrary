package com.LabWork.app.MangaStore.service.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id [%s] is not found", id));
    }
    public UserNotFoundException(String login) {
        super(String.format("User not found '%s'", login));
    }
}
