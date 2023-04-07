package com.LabWork.app.MangaStore.model.Dto.SupportDto;

import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;

import java.util.List;

public class MangaDto {
    private final Long id;

    private final Long creatorId;
    private final String mangaName;
    private final Integer chapterCount;

    public MangaDto(Manga manga) {
        this.id = manga.getId();
        this.creatorId = manga.getCreator().getId();
        this.mangaName = manga.getMangaName();
        this.chapterCount = manga.getChapterCount();
    }

    public Long getId() {
        return id;
    }

    public String getMangaName() {
        return mangaName;
    }

    public Integer getChapterCount() {
        return chapterCount;
    }

    public Long getCreatorId() {
        return creatorId;
    }

}
