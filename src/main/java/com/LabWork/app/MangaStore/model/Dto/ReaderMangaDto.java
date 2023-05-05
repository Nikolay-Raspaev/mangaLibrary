package com.LabWork.app.MangaStore.model.Dto;

import com.LabWork.app.MangaStore.model.Default.Reader;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;

import java.util.List;

public class ReaderMangaDto {
    private Long id;

    private String readerName;

    private String hashedPassword;

    private List<MangaDto> mangas;

    public ReaderMangaDto() {
    }

    public ReaderMangaDto(Reader reader) {
        this.id = reader.getId();
        this.mangas = reader.getMangas().stream()
                .map(y -> new MangaDto(y))
                .toList();
    }

    public Long getId() {
        return id;
    }

    public String getReaderName() { return readerName; }

    public String getHashedPassword() { return hashedPassword; }

    public List<MangaDto> getMangas() { return mangas; }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setMangas(List<MangaDto> mangas) {
        this.mangas = mangas;
    }

}
