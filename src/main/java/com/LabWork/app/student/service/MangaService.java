package com.LabWork.app.student.service;

import com.LabWork.app.student.model.Creator;
import com.LabWork.app.student.model.Manga;
import com.LabWork.app.student.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MangaService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Manga findManga(Long id) {
        final Manga manga = em.find(Manga.class, id);
        if (manga == null) {
            throw new EntityNotFoundException(String.format("Manga with id [%s] is not found", id));
        }
        return manga;
    }

    @Transactional
    public List<Manga> findAllMangs() {
        return em.createQuery("select c from Manga c", Manga.class).getResultList();
    }

    @Transactional
    public Manga addManga(Creator creator, Integer chapterCount, String mangaName) {
        if (creator == null) {
            throw new IllegalArgumentException("Invalid creator");
        }
        if (chapterCount < 0 || chapterCount == null) {
            throw new IllegalArgumentException("Invalid chapterCount");
        }
        if (!StringUtils.hasText(mangaName)) {
            throw new IllegalArgumentException("Invalid mangaName");
        }
        final Manga manga = new Manga(chapterCount, mangaName, creator);
        manga.getCreator().getMangs().add(manga);
        em.persist(manga);
        return manga;
    }

    @Transactional
    public Manga updateManga(Long id, Integer chapterCount) {
        if (chapterCount < 0 || chapterCount == null) {
            throw new IllegalArgumentException("Invalid chapterCount");
        }
        final Manga manga = findManga(id);
        manga.setChapterCount(chapterCount);
        return em.merge(manga);
    }

    @Transactional
    public Manga deleteManga(Long id) {
        final Manga currentManga = findManga(id);
        List<User> userList= currentManga.getUsers();
        for (User user : userList) {
            user.getMangs().remove(currentManga);
        }
        em.remove(currentManga);
        return currentManga;
    }

    @Transactional
    public void deleteAllMangs() {
        em.createQuery("delete from Manga").executeUpdate();
    }
}
