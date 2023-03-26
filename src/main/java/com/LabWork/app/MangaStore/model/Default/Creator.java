package com.LabWork.app.MangaStore.model.Default;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "creatorName can't be null or empty")
    @Column
    private String creatorName;

    @NotBlank(message = "hashedPassword can't be null or empty")
    @Column
    private String hashedPassword;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creator", cascade = CascadeType.REMOVE)
    private List<Manga> mangas;

    public Creator() {
    }

    public Creator(String creatorName, String hashedPassword) {
        this.creatorName = creatorName;
        this.hashedPassword = hashedPassword;
        this.mangas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<Manga> getMangas() {
        return mangas;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setMangas(List<Manga> mangas) {
        this.mangas = mangas;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

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
