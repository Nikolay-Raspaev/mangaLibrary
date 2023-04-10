package com.LabWork.app.MangaStore.service.Repository;

import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MangaRepository extends JpaRepository<Manga, Long> {
    @Query("select r from Reader r where :manga MEMBER OF r.mangas")
    List<Reader> getReaders(Manga manga);
}


