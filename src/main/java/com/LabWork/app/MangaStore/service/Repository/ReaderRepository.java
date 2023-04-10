package com.LabWork.app.MangaStore.service.Repository;

import com.LabWork.app.MangaStore.model.Default.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
