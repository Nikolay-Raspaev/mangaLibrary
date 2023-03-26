package com.LabWork.app.MangaStore.service;

import com.LabWork.app.MangaStore.model.Manga;
import com.LabWork.app.MangaStore.model.Reader;
import com.LabWork.app.MangaStore.repository.MangaRepository;
import com.LabWork.app.MangaStore.repository.ReaderRepository;
import com.LabWork.app.MangaStore.service.Exception.MangaNotFoundException;
import com.LabWork.app.MangaStore.service.Exception.ReaderNotFoundException;
import com.LabWork.app.MangaStore.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    private final MangaRepository mangaRepository;
    private final ValidatorUtil validatorUtil;

    public ReaderService(ReaderRepository readerRepository, MangaRepository mangaRepository, ValidatorUtil validatorUtil) {
        this.readerRepository = readerRepository;
        this.mangaRepository = mangaRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Reader findReader(Long id) {
        final Optional<Reader> reader = readerRepository.findById(id);
        return reader.orElseThrow(() -> new ReaderNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Manga findManga(Long id) {
        final Optional<Manga> manga = mangaRepository.findById(id);
        return manga.orElseThrow(() -> new MangaNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Reader> findAllReaders() {
        return readerRepository.findAll();
    }

    @Transactional
    public Reader addReader(String readerName, String password) {
        final Reader reader = new Reader(readerName, password);
        validatorUtil.validate(reader);
        return readerRepository.save(reader);
    }

    //СКОРЕЕ ВСЕГО НЕ БУДЕТ РАБОТАТЬ
    @Transactional
    public Reader addManga(Long mangaId, Long readerId) {
        final Manga manga = findManga(mangaId);
        final Reader reader = findReader(readerId);
        validatorUtil.validate(reader);
        reader.getMangas().add(manga);
        manga.getReaders().add(reader);
        return readerRepository.save(reader);
    }

    @Transactional
    public Reader removeManga(Long mangaId, Long readerId) {
        //em.createNativeQuery("delete from Mangas_Readers where MANGA_FK = " + manga.getId() + " AND READER_FK = "+ readerId).executeUpdate();
        final Reader currentReader = findReader(readerId);
        final Manga currentManga = findManga(mangaId);
        currentReader.getMangas().remove(currentManga);
        currentManga.getReaders().remove(currentReader);
        mangaRepository.save(currentManga);
        return readerRepository.save(currentReader);
    }

    @Transactional
    public Reader updateReader(Long id, String readername, String password) {
        final Reader currentReader = findReader(id);
        currentReader.setReaderName(readername);
        currentReader.setHashedPassword(password);
        validatorUtil.validate(currentReader);
        return readerRepository.save(currentReader);
    }

    @Transactional
    public Reader deleteReader(Long id) {
        final Reader currentReader = findReader(id);
/*        for (Manga manga : currentReader.getMangas()){
            manga.getReaders().remove(currentReader);
        }
        em.merge(currentReader);
        em.remove(currentReader);*/
        readerRepository.delete(currentReader);
        return currentReader;
    }

    @Transactional
    public void deleteAllReaders() {
        readerRepository.deleteAll();
    }
}
