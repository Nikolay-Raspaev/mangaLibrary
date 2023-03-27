package com.LabWork.app.MangaStore.model.Dto;

import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import java.util.List;
import java.util.Objects;

public class ReaderDto {
    private Long id;

    private String readerName;

    private String hashedPassword;

    private List<String> mangas;

    public ReaderDto(Reader reader) {
        this.id = reader.getId();
        this.readerName = reader.getReaderName();
        this.hashedPassword = reader.getHashedPassword();
        this.mangas = reader.getMangas().stream()
                .map(y -> new String(y.getMangaName()))
                .toList();
    }

    public Long getId() {
        return id;
    }

    public String getReaderName() { return readerName; }

    public String getHashedPassword() { return hashedPassword; }

    public List<String> getMangas() { return mangas; }

}
