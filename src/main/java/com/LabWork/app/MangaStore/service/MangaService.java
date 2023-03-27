package com.LabWork.app.MangaStore.service;

import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import com.LabWork.app.MangaStore.service.Repository.CreatorRepository;
import com.LabWork.app.MangaStore.service.Repository.MangaRepository;
import com.LabWork.app.MangaStore.service.Exception.CreatorNotFoundException;
import com.LabWork.app.MangaStore.service.Exception.MangaNotFoundException;
import com.LabWork.app.MangaStore.service.Repository.ReaderRepository;
import com.LabWork.app.MangaStore.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MangaService {
    public final MangaRepository mangaRepository;
    public final CreatorRepository creatorRepository;

    public final ReaderRepository readerRepository;
    private final ValidatorUtil validatorUtil;

    public MangaService(MangaRepository mangaRepository, CreatorRepository creatorRepository, ReaderRepository readerRepository,
                        ValidatorUtil validatorUtil) {
        this.mangaRepository = mangaRepository;
        this.readerRepository = readerRepository;
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

    @Transactional
    public List<Reader> getReader(Long id) {
        //em.createNativeQuery("delete from Mangas_Readers where MANGA_FK = " + manga.getId() + " AND READER_FK = "+ readerId).executeUpdate();
        //SELECT b FROM Book b WHERE ?1 MEMBER OF b.genres
        //final List<Reader> listReader = em.createQuery("select r from Reader r where " + id + " MEMBER OF r.mangas", Reader.class).getResultList();
        List<Reader> listReader = new ArrayList<>();
        for (Reader reader : readerRepository.findAll()){
            for (Manga manga : reader.getMangas()){
                if (manga.getId() == id){
                    listReader.add(reader);
                }
            }
        }
        return listReader;
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
        final List<Reader> listReader = readerRepository.findAll();
        for (Reader reader : listReader){
            reader.getMangas().remove(currentManga);
            readerRepository.save(reader);
        }
        mangaRepository.delete(currentManga);
        return currentManga;
    }

    @Transactional
    public void deleteAllMangas() {
        mangaRepository.deleteAll();
    }
}
