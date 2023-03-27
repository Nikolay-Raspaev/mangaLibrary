package com.LabWork.app.student.service;

import com.LabWork.app.student.model.Creator;
import com.LabWork.app.student.model.Manga;
import com.LabWork.app.student.model.Reader;
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
    public List<Manga> findAllMangas() {
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
        final Manga manga = new Manga(creator, mangaName, chapterCount);
        manga.getCreator().getMangas().add(manga);
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
        em.merge(manga);
        return manga;
    }

    @Transactional
    public List<Reader> getReader(Long id) {
        //em.createNativeQuery("delete from Mangas_Readers where MANGA_FK = " + manga.getId() + " AND READER_FK = "+ readerId).executeUpdate();
        //SELECT b FROM Book b WHERE ?1 MEMBER OF b.genres
        final List<Reader> listReader = em.createQuery("select r from Reader r where " + id + " MEMBER OF r.mangas", Reader.class).getResultList();
        return listReader;
    }

    @Transactional
    public Manga deleteManga(Long id) {
        final Manga currentManga = findManga(id);
        final List<Reader> listReader = em.createQuery("select c from Reader c", Reader.class).getResultList();
        for (Reader reader : listReader){
            reader.getMangas().remove(currentManga);
            em.merge(reader);
        }
        em.remove(currentManga);
        return currentManga;
    }

    @Transactional
    public void deleteAllMangas() {
        em.createQuery("delete from Manga").executeUpdate();
    }
}
