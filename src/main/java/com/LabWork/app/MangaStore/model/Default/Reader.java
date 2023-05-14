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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    /*@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)*/
    /*orphanRemoval=true*/
    private List<Manga> mangas;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Reader(User user) {
        this.mangas = new ArrayList<>();
        this.user = user;
    }

    public Reader() {
    }


    public Long getId() {
        return id;
    }

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
                '}';
    }
}
