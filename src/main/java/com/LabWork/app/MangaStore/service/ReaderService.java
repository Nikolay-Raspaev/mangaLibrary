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
    private final ValidatorUtil validatorUtil;

    public ReaderService(ReaderRepository readerRepository, ValidatorUtil validatorUtil) {
        this.readerRepository = readerRepository;
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
        return readerRepository.save(currentReader);
    }

    public void addManga(Long readerId, List<Manga> mangas) {
        readerRepository.findById(readerId).get().setMangas(mangas);
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
}
