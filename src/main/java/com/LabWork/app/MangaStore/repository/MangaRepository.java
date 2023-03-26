package com.LabWork.app.MangaStore.repository;

import com.LabWork.app.MangaStore.model.Manga;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MangaRepository extends JpaRepository<Manga, Long> {
}
