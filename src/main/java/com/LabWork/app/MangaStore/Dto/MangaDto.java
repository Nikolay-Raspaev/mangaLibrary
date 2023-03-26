package com.LabWork.app.MangaStore.Dto;

import com.LabWork.app.MangaStore.model.Creator;
import com.LabWork.app.MangaStore.model.Manga;
import com.LabWork.app.MangaStore.model.Reader;
import java.util.List;

public class MangaDto {

    private final Long id;

    private final Creator creator;
    private final String mangaName;
    private final Integer chapterCount;

    private final List<Reader> readers;

    public MangaDto(Manga manga) {
        this.id = manga.getId();
        this.creator = manga.getCreator();
        this.mangaName = manga.getMangaName();
        this.chapterCount = manga.getChapterCount();
        this.readers = manga.getReaders();
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

    public Creator getCreator() {
        return creator;
    }

    public List<Reader> getReaders() {
        return readers;
    }

}
