package com.LabWork.app.MangaStore.model.Default;

import javax.persistence.*;

import com.LabWork.app.MangaStore.user.model.User;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Reader {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    /*@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)*/
    /*orphanRemoval=true*/
    private List<Manga> mangas;

    public Reader() {
    }

    public Reader(String creatorName, String hashedPassword) {
        this.user = user;
        this.id = user.getId();
        this.mangas = new ArrayList<>();
    }

    public Reader(User user) {
        this.user = user;
        this.id = user.getId();
        this.mangas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<Manga> getMangas() { return mangas; }

    public void setMangas(List<Manga> mangas) { this.mangas = mangas; }

    public User getUser() { return user; }

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
                '}';
    }
}
