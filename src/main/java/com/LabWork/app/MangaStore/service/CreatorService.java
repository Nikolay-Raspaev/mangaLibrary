package com.LabWork.app.MangaStore.service;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import com.LabWork.app.MangaStore.service.Exception.MangaNotFoundException;
import com.LabWork.app.MangaStore.service.Repository.CreatorRepository;
import com.LabWork.app.MangaStore.service.Exception.CreatorNotFoundException;
import com.LabWork.app.MangaStore.service.Repository.MangaRepository;
import com.LabWork.app.MangaStore.service.Repository.ReaderRepository;
import com.LabWork.app.MangaStore.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CreatorService {
    private final CreatorRepository creatorRepository;
    private final ReaderRepository readerRepository;
    private final MangaRepository mangaRepository;
    private final ValidatorUtil validatorUtil;

    public CreatorService(CreatorRepository creatorRepository, MangaRepository mangaRepository, ReaderRepository readerRepository,
                          ValidatorUtil validatorUtil) {
        this.creatorRepository = creatorRepository;
        this.readerRepository = readerRepository;
        this.mangaRepository = mangaRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional(readOnly = true)
    public Creator findCreator(Long id) {
        final Optional<Creator> creator = creatorRepository.findById(id);
        return creator.orElseThrow(() -> new CreatorNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Creator> findAllCreators() { return creatorRepository.findAll(); }


    @Transactional
    public Creator addCreator(String creatorName, String password) {
        final Creator creator = new Creator(creatorName, password);
        validatorUtil.validate(creator);
        return creatorRepository.save(creator);
    }

    @Transactional
    public Creator updateCreator(Long id, String creatorName, String password) {
        final Creator currentCreator = findCreator(id);
        currentCreator.setCreatorName(creatorName);
        currentCreator.setHashedPassword(password);
        validatorUtil.validate(currentCreator);
        return creatorRepository.save(currentCreator);
    }

    @Transactional(readOnly = true)
    public List<Manga> findAllMangas() {
        return mangaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Manga findManga(Long id) {
        final Optional<Manga> manga = mangaRepository.findById(id);
        return manga.orElseThrow(() -> new MangaNotFoundException(id));
    }
    @Transactional
    public List<Reader> getReader(Long id) {
        final Manga currentManga = findManga(id);
        final List<Reader> listReader = mangaRepository.getReaders(currentManga);
        return listReader;
    }

    @Transactional
    public Creator deleteCreator(Long id) {
        final Creator currentCreator = findCreator(id);
        List<Manga> listManga = currentCreator.getMangas();
        findAllMangas();
        for (Manga manga : listManga){
            for (final Reader reader :getReader(manga.getId())){
                reader.getMangas().remove(manga);
                readerRepository.save(reader);
            }
        }
        creatorRepository.delete(currentCreator);
        return currentCreator;
    }

    @Transactional
    public void deleteAllCreators() {
        creatorRepository.deleteAll();
    }

/*
      //бесполезная штука
    @Transactional
    public Creator addManga(Long creatorId, Manga manga) {
        final Creator creator = findCreator(creatorId);
        creator.getMangas().add(manga);
        em.merge(creator);
        return creator;
    }*/

/*    //бесполезная штука
    @Transactional
    public Manga deleteManga(Long creatorId, Manga manga) {
        Creator creator = findCreator(creatorId);
        if (creator.getMangas().contains(manga)){
            final Manga currentManga = em.createQuery("select m from Manga m where m.id = " + manga.getId(), Manga.class).getSingleResult();
            em.remove(currentManga);
            return currentManga;
        }
        return null;
    }*/
}
