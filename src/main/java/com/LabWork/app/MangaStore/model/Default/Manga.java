package com.LabWork.app.MangaStore.model.Default;

import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.CreatorService;
import com.LabWork.app.MangaStore.service.MangaService;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String mangaName;

    @Column
    private Integer chapterCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="creator_fk")
    private Creator creator;

    @Lob
    private byte[] image;
    public Manga() {
    }

    public Manga(Creator creator, String mangaName, Integer chapterCount, String image) {
        this.creator = creator;
        this.mangaName = mangaName;
        this.chapterCount = chapterCount;
        this.image = image.getBytes();
    }

    public Manga(Creator creator, MangaDto mangaDto) {
        this.creator = creator;
        this.mangaName = mangaDto.getMangaName();
        this.chapterCount = mangaDto.getChapterCount();
        this.image = mangaDto.getImage().getBytes();
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public Long getCreatorId() {
        return creator.getId();
    }

    public String getMangaName() {
        return mangaName;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName;
    }

    public Integer getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(Integer chapterCount) {
        this.chapterCount = chapterCount;
    }

    public Creator getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "Manga{" +
                "id=" + id +
                ", mangaName='" + mangaName + '\'' +
                ", chapterCount='" + chapterCount + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manga manga = (Manga) o;
        return Objects.equals(id, manga.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
