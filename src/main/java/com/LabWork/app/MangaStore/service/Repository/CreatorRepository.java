package com.LabWork.app.MangaStore.service.Repository;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
}
