package com.LabWork.app.student.model;

import jakarta.persistence.*;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "mangas_readers",
            joinColumns = @JoinColumn(name = "manga_fk"),
            inverseJoinColumns = @JoinColumn(name = "reader_fk"))
    private List<Reader> readers;

    public Manga() {
    }

    public Manga(Creator creator, String mangaName, Integer chapterCount) {
        this.creator = creator;
        this.mangaName = mangaName;
        this.chapterCount = chapterCount;
        this.readers = new ArrayList<>();
    }

    public Long getId() {
        return id;
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

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
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
