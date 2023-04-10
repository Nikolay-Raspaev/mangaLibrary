package com.LabWork.app.MangaStore.model.Default;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String readerName;

    @Column
    private String hashedPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    /*@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)*/
    /*orphanRemoval=true*/
    private List<Manga> mangas;

    public Reader() {
    }

    public Reader(String readerName, String hashedPassword) {
        this.readerName = readerName;
        this.hashedPassword = hashedPassword;
        this.mangas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }



    public String getReaderName() { return readerName; }

    public void setReaderName(String readerName) { this.readerName = readerName; }

    public String getHashedPassword() { return hashedPassword; }

    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

    public List<Manga> getMangas() { return mangas; }

    public void setMangas(List<Manga> mangas) { this.mangas = mangas; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(id, reader.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", readerName='" + readerName + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                '}';
    }
}
