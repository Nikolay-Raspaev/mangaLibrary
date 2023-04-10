package com.LabWork.app.MangaStore.service;
import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Default.Manga;
import com.LabWork.app.MangaStore.model.Default.Reader;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.Repository.CreatorRepository;
import com.LabWork.app.MangaStore.service.Repository.MangaRepository;
import com.LabWork.app.MangaStore.service.Exception.CreatorNotFoundException;
import com.LabWork.app.MangaStore.service.Exception.MangaNotFoundException;
import com.LabWork.app.MangaStore.service.Repository.ReaderRepository;
import com.LabWork.app.MangaStore.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MangaService {
    public final MangaRepository mangaRepository;
    public final CreatorRepository creatorRepository;
    public final ReaderService readerService;
    private final ValidatorUtil validatorUtil;

    public MangaService(MangaRepository mangaRepository,
                        CreatorRepository  creatorRepository,
                        ReaderService readerService,
                        ValidatorUtil validatorUtil) {
        this.mangaRepository = mangaRepository;
        this.readerService = readerService;
        this.creatorRepository  = creatorRepository;
        this.validatorUtil = validatorUtil;
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

    @Transactional(readOnly = true)
    public List<Manga> findAllMangas() {
        return mangaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Creator findCreator(Long id) {
        final Optional<Creator> creator = creatorRepository.findById(id);
        return creator.orElseThrow(() -> new CreatorNotFoundException(id));
    }

    @Transactional
    public Manga addManga(Long creatorId, Integer chapterCount, String mangaName) {
        final Creator currentCreator = findCreator(creatorId);
        final Manga manga = new Manga(currentCreator, mangaName, chapterCount);
        validatorUtil.validate(manga);
        return mangaRepository.save(manga);
    }

    @Transactional
    public Manga addManga(MangaDto mangaDto) {
        final Creator currentCreator = findCreator(mangaDto.getCreatorId());
        final Manga manga = new Manga(currentCreator, mangaDto);
        validatorUtil.validate(manga);
        return mangaRepository.save(manga);
    }

    @Transactional
    public Manga updateManga(Long id, Integer chapterCount) {
        final Manga currentManga = findManga(id);
        currentManga.setChapterCount(chapterCount);
        validatorUtil.validate(currentManga);
        return currentManga;
    }

    @Transactional
    public Manga updateManga(MangaDto mangaDto) {
        final Manga currentManga = findManga(mangaDto.getId());
        currentManga.setChapterCount(mangaDto.getChapterCount());
        currentManga.setImage(mangaDto.getImage().getBytes());
        validatorUtil.validate(currentManga);
        return currentManga;
    }

    @Transactional
    public Manga deleteManga(Long id) {
        final Manga currentManga = findManga(id);
        final List<Reader> listReader = readerService.findAllReaders();
/*        for (Reader reader : listReader){
            reader.getMangas().remove(currentManga);
        }*/
        mangaRepository.delete(currentManga);
        return currentManga;
    }

    @Transactional
    public void deleteAllMangas() {
        mangaRepository.deleteAll();
    }
}
