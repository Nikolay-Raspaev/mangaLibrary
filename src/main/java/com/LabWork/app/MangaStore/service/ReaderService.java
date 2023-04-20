package com.LabWork.app.MangaStore.service;

import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import com.LabWork.app.MangaStore.service.Repository.MangaRepository;
import com.LabWork.app.MangaStore.service.Repository.ReaderRepository;
import com.LabWork.app.MangaStore.service.Exception.MangaNotFoundException;
import com.LabWork.app.MangaStore.service.Exception.ReaderNotFoundException;
import com.LabWork.app.MangaStore.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final MangaRepository mangaRepository;
    private final ValidatorUtil validatorUtil;

    public ReaderService(ReaderRepository readerRepository,
                         ValidatorUtil validatorUtil,
                         MangaRepository mangaRepository) {
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
    public List<Reader> findAllReaders() {
        return readerRepository.findAll();
    }

    @Transactional
    public Reader addReader(String readerName, String password) {
        final Reader reader = new Reader(readerName, password);
        validatorUtil.validate(reader);
        return readerRepository.save(reader);
    }

    @Transactional
    public Reader updateReader(Long id, String readername, String password) {
        final Reader currentReader = findReader(id);
        currentReader.setReaderName(readername);
        currentReader.setHashedPassword(password);
        validatorUtil.validate(currentReader);
        return currentReader;
    }

    @Transactional
    public Reader deleteReader(Long id) {
        final Reader currentReader = findReader(id);
        currentReader.getMangas().clear();
        readerRepository.delete(currentReader);
        return currentReader;
    }

    @Transactional
    public void deleteAllReaders() {
        readerRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public Manga findManga(Long id) {
        final Optional<Manga> manga = mangaRepository.findById(id);
        return manga.orElseThrow(() -> new MangaNotFoundException(id));
    }

    @Transactional
    public Manga addManga(Long mangaId, Long readerId) {
        final Manga manga = findManga(mangaId);
        final Reader reader = findReader(readerId);
        validatorUtil.validate(reader);
        if (reader.getMangas().contains(manga))
        {
            return null;
        }
        reader.getMangas().add(manga);
        return manga;
    }

    @Transactional
    public Manga removeManga(Long mangaId, Long readerId) {
        final Reader currentReader = findReader(readerId);
        final Manga currentManga = findManga(mangaId);
        currentReader.getMangas().remove(currentManga);
        return currentManga;
    }
}
