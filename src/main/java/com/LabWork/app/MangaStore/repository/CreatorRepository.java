package com.LabWork.app.MangaStore.repository;

import com.LabWork.app.MangaStore.model.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
}
