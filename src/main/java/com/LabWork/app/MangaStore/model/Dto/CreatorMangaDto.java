package com.LabWork.app.MangaStore.model.Dto;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;

import java.util.List;

public class CreatorMangaDto {
    private long id;
    private String creatorName;
    private String hashedPassword;
    private List<MangaDto> mangas;

    public CreatorMangaDto() {
    }

    public CreatorMangaDto(Creator creator) {
        this.id = creator.getId();
        this.mangas = creator.getMangas().stream()
                .map(x -> new MangaDto(x))
                .toList();
    }

    public long getId() {
        return id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<MangaDto> getMangas() { return mangas; }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setMangas(List<MangaDto> mangas) {
        this.mangas = mangas;
    }
}
