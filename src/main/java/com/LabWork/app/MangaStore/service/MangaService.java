package com.LabWork.app.MangaStore.service;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.service.Repository.CreatorRepository;
import com.LabWork.app.MangaStore.service.Repository.MangaRepository;
import com.LabWork.app.MangaStore.service.Exception.CreatorNotFoundException;
import com.LabWork.app.MangaStore.service.Exception.MangaNotFoundException;
import com.LabWork.app.MangaStore.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MangaService {
    public final MangaRepository mangaRepository;
    public final CreatorRepository creatorRepository;
    private final ValidatorUtil validatorUtil;

    public MangaService(MangaRepository mangaRepository, CreatorRepository creatorRepository,
                        ValidatorUtil validatorUtil) {
        this.mangaRepository = mangaRepository;
        this.creatorRepository = creatorRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional(readOnly = true)
    public Manga findManga(Long id) {
        final Optional<Manga> manga = mangaRepository.findById(id);
        return manga.orElseThrow(() -> new MangaNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Creator findCreator(Long id) {
        final Optional<Creator> creator = creatorRepository.findById(id);
        return creator.orElseThrow(() -> new CreatorNotFoundException(id));
    }


    @Transactional(readOnly = true)
    public List<Manga> findAllMangas() {
        return mangaRepository.findAll();
    }

    @Transactional
    public Manga addManga(Long creatorId, Integer chapterCount, String mangaName) {
        final Creator currentCreator = findCreator(creatorId);
        final Manga manga = new Manga(currentCreator, mangaName, chapterCount);
        //manga.getCreator().getMangas().add(manga);
        //в случае чего можно взять создателя из бд и изменить его здесь же
        validatorUtil.validate(manga);
        return mangaRepository.save(manga);
    }

    @Transactional
    public Manga updateManga(Long id, Integer chapterCount) {
        final Manga currentManga = findManga(id);
        currentManga.setChapterCount(chapterCount);
        validatorUtil.validate(currentManga);
        return mangaRepository.save(currentManga);
    }

    @Transactional
    public Manga deleteManga(Long id) {
        final Manga currentManga = findManga(id);
        mangaRepository.delete(currentManga);
        return currentManga;
    }

    @Transactional
    public void deleteAllMangas() {
        mangaRepository.deleteAll();
    }
}
