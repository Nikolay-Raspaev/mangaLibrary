package com.LabWork.app.student.service;

import com.LabWork.app.student.model.Manga;
import com.LabWork.app.student.model.Reader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ReaderService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private MangaService mangaService;

    @Transactional
    public Reader findReader(Long id) {
        final Reader reader = em.find(Reader.class, id);
        if (reader == null) {
            throw new EntityNotFoundException(String.format("Reader with id [%s] is not found", id));
        }
        return reader;
    }

    @Transactional
    public List<Reader> findAllReaders() {
        return em.createQuery("select c from Reader c", Reader.class).getResultList();
    }

    @Transactional
    public Reader addReader(String readername, String password) {
        if (!StringUtils.hasText(readername) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Customer's readername or password is empty");
        }
        final Reader reader = new Reader(readername, password);
        em.persist(reader);
        return reader;
    }

    @Transactional
    public void addManga(Manga manga, Long readerId) {
        final Reader reader = findReader(readerId);
        reader.getMangas().add(manga);
        manga.getReaders().add(reader);
        em.merge(reader);
    }

    @Transactional
    public void removeManga(Manga manga, Long readerId) {
        //em.createNativeQuery("delete from Mangas_Readers where MANGA_FK = " + manga.getId() + " AND READER_FK = "+ readerId).executeUpdate();
        final Reader currentReader = findReader(readerId);
        final Manga currentManga = em.find(Manga.class, manga.getId());
        currentReader.getMangas().remove(currentManga);
        currentManga.getReaders().remove(currentReader);
        em.merge(currentReader);
        em.merge(currentManga);
    }

    @Transactional
    public Reader updateReader(Long id, String readername, String password) {
        if (!StringUtils.hasText(readername) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Customer's readername or password is empty");
        }
        final Reader reader = findReader(id);
        reader.setReaderName(readername);
        reader.setHashedPassword(password);
        return em.merge(reader);
    }

    @Transactional
    public Reader deleteReader(Long id) {
        final Reader currentReader = findReader(id);
        for (Manga manga : currentReader.getMangas()){
            manga.getReaders().remove(currentReader);
        }
        em.merge(currentReader);
        em.remove(currentReader);
        return currentReader;
    }

    @Transactional
    public void deleteAllReaders() {
        em.createQuery("delete from Reader").executeUpdate();
    }
}
