package com.LabWork.app.MangaStore.service.Repository;

import com.LabWork.app.MangaStore.model.Default.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByLoginIgnoreCase(String login);
}
