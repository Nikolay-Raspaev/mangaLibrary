package com.LabWork.app.MangaStore.model.Dto;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import com.LabWork.app.MangaStore.service.MangaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MangaDto {
    private final Long id;

    private final Long creatorId;
    private final String mangaName;
    private final Integer chapterCount;

    private final List<String> readers;

    public MangaDto(Manga manga, List<Reader> listReader) {
        this.id = manga.getId();
        this.creatorId = manga.getCreator().getId();
        this.mangaName = manga.getMangaName();
        this.chapterCount = manga.getChapterCount();
        this.readers = listReader.stream()
                .map(y -> new String(y.getReaderName()))
                .toList();
    }

    public MangaDto(Manga manga) {
        this.id = manga.getId();
        this.creatorId = manga.getCreator().getId();
        this.mangaName = manga.getMangaName();
        this.chapterCount = manga.getChapterCount();
        this.readers = null;
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
