package com.LabWork.app.MangaStore.service.Repository;

import com.LabWork.app.MangaStore.model.Default.Manga;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MangaRepository extends JpaRepository<Manga, Long> {
}
