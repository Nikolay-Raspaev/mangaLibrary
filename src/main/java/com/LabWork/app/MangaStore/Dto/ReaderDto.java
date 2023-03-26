package com.LabWork.app.MangaStore.Dto;

import com.LabWork.app.MangaStore.model.Manga;
import com.LabWork.app.MangaStore.model.Reader;
import java.util.List;

public class ReaderDto {
    private Long id;

    private String readerName;

    private String hashedPassword;

    private List<Manga> mangas;

    public ReaderDto(Reader reader) {
        this.id = reader.getId();
        this.readerName = reader.getReaderName();
        this.hashedPassword = reader.getHashedPassword();
        this.mangas = reader.getMangas();
    }

    public Long getId() {
        return id;
    }

    public String getReaderName() { return readerName; }

    public String getHashedPassword() { return hashedPassword; }

    public List<Manga> getMangas() { return mangas; }

}
