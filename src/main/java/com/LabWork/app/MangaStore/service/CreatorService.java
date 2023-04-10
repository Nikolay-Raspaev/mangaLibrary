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
    private final MangaService mangaService;
    private final ValidatorUtil validatorUtil;

    public CreatorService(CreatorRepository creatorRepository,
                          MangaService mangaService,
                          ValidatorUtil validatorUtil) {
        this.creatorRepository = creatorRepository;
        this.mangaService = mangaService;
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
        return currentCreator;
    }

    @Transactional
    public Creator deleteCreator(Long id) {
        final Creator currentCreator = findCreator(id);
        List<Manga> listManga = currentCreator.getMangas();
        for (Manga manga : listManga){
            for (final Reader reader :mangaService.getReader(manga.getId())){
                reader.getMangas().remove(manga);
            }
        }
        creatorRepository.delete(currentCreator);
        return currentCreator;
    }

    @Transactional
    public void deleteAllCreators() {
        creatorRepository.deleteAll();
    }
}
