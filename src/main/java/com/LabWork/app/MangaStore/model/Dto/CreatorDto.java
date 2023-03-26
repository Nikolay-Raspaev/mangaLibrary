package com.LabWork.app.MangaStore.model.Dto;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import java.util.List;

public class CreatorDto {
    private final long id;
    private final String creatorName;
    private final String hashedPassword;
    private final List<Manga> mangas;

    public CreatorDto(Creator creator) {
        this.id = creator.getId();
        this.creatorName = creator.getCreatorName();
        this.hashedPassword = creator.getHashedPassword();
        this.mangas = creator.getMangas();
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

    public List<Manga> getMangas() { return mangas; }
}
