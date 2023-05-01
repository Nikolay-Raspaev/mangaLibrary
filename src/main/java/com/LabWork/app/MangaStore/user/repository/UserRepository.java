package com.LabWork.app.MangaStore.user.repository;

import com.LabWork.app.MangaStore.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByLoginIgnoreCase(String login);
}
