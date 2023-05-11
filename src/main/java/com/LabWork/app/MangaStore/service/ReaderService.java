package com.LabWork.app.MangaStore.service;

import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import com.LabWork.app.MangaStore.service.Exception.CreatorNotFoundException;
import com.LabWork.app.MangaStore.service.Repository.MangaRepository;
import com.LabWork.app.MangaStore.service.Repository.ReaderRepository;
import com.LabWork.app.MangaStore.service.Exception.MangaNotFoundException;
import com.LabWork.app.MangaStore.service.Exception.ReaderNotFoundException;
import com.LabWork.app.MangaStore.model.Default.User;
import com.LabWork.app.MangaStore.service.Repository.UserRepository;
import com.LabWork.app.MangaStore.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final UserRepository userRepository;
    private final MangaRepository mangaRepository;
    private final ValidatorUtil validatorUtil;

    public ReaderService(ReaderRepository readerRepository,
                         ValidatorUtil validatorUtil,
                         UserRepository userRepository,
                         MangaRepository mangaRepository) {
        this.readerRepository = readerRepository;
        this.mangaRepository = mangaRepository;
        this.validatorUtil = validatorUtil;
        this.userRepository = userRepository;
    }

    @Transactional
    public Reader findReader(Long id) {
        final Optional<Reader> reader = readerRepository.findById(id);
        return reader.orElseThrow(() -> new ReaderNotFoundException(id));
    }

    public Reader findByLogin(String login) {
        return findReader(userRepository.findOneByLoginIgnoreCase(login).getId());
    }

    @Transactional(readOnly = true)
    public List<Reader> findAllReaders() {
        return readerRepository.findAll();
    }

    @Transactional
    public Reader addReader(User user) {
        final Reader reader = new Reader(user);
        validatorUtil.validate(reader);
        return readerRepository.save(reader);
    }

    @Transactional(readOnly = true)
    public Manga findManga(Long id) {
        final Optional<Manga> manga = mangaRepository.findById(id);
        return manga.orElseThrow(() -> new MangaNotFoundException(id));
    }

    @Transactional
    public Manga addManga(Long mangaId, String readerLogin) {
        final Manga manga = findManga(mangaId);
        final Reader reader = findByLogin(readerLogin);
        validatorUtil.validate(reader);
        if (reader.getMangas().contains(manga))
        {
            return null;
        }
        reader.getMangas().add(manga);
        return manga;
    }

    @Transactional
    public Manga removeManga(Long mangaId, String readerLogin) {
        final Reader currentReader = findByLogin(readerLogin);
        final Manga currentManga = findManga(mangaId);
        currentReader.getMangas().remove(currentManga);
        return currentManga;
    }
}
