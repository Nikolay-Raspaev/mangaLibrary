package com.LabWork.app.MangaStore.model.Dto.SupportDto;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MangaDto {
    private Long id;
    private Long creatorId;
    private String mangaName;
    private Integer chapterCount;
    private String image;

    public MangaDto() {
    }

    public MangaDto(Manga manga) {
        this.id = manga.getId();
        this.creatorId = manga.getCreator().getId();
        this.mangaName = manga.getMangaName();
        this.chapterCount = manga.getChapterCount();
        this.image = new String(manga.getImage(), StandardCharsets.UTF_8);
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

    public String getImage() {
        return image;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName;
    }

    public void setChapterCount(Integer chapterCount) {
        this.chapterCount = chapterCount;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setImage(String image) {this.image = image;}
}
