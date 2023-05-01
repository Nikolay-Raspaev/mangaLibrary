package com.LabWork.app.MangaStore.model.Default;

import com.LabWork.app.MangaStore.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Creator {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creator", cascade = CascadeType.REMOVE)
    private List<Manga> mangas;

    public Creator() {
    }

    public Creator(String creatorName, String hashedPassword) {
        this.user = user;
        this.mangas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<Manga> getMangas() {
        return mangas;
    }

    public void setMangas(List<Manga> mangas) {
        this.mangas = mangas;
    }

    public User getUser() { return user; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creator creator = (Creator) o;
        return Objects.equals(id, creator.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Creator{" +
                "id=" + id +
                ", creatorName='" + creatorName + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                '}';
    }
}
