package com.LabWork.app.MangaStore.model.Dto;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import java.util.List;
import java.util.Objects;

public class CreatorDto {
    private final long id;
    private final String creatorName;
    private final String hashedPassword;
    private final List<MangaDto> mangas;

    public CreatorDto(Creator creator) {
        this.id = creator.getId();
        this.creatorName = creator.getCreatorName();
        this.hashedPassword = creator.getHashedPassword();
        this.mangas = creator.getMangas().stream()
                .map(y -> new MangaDto(y))
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
}
