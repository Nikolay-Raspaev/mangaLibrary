package com.LabWork.app.student.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String creatorName;

    @Column
    private String hashedPassword;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creator")
    private List<Manga> mangs;

    public Creator() {
    }

    public Creator(String creatorName, String hashedPassword) {
        this.creatorName = creatorName;
        this.hashedPassword = hashedPassword;
        this.mangs = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<Manga> getMangs() {
        return mangs;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setMangs(List<Manga> mangs) {
        this.mangs = mangs;
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
}
