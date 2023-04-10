package com.LabWork.app.MangaStore.controller;


import com.LabWork.app.MangaStore.model.Dto.ReaderMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.CreatorService;
import com.LabWork.app.MangaStore.service.MangaService;
import com.LabWork.app.MangaStore.service.ReaderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    private final ReaderService readerService;
    private final MangaService mangaService;

    public ReaderController(ReaderService readerService,
                            MangaService mangaService) {
        this.readerService = readerService;
        this.mangaService = mangaService;
    }

    @GetMapping("/{id}")
    public ReaderMangaDto getReader(@PathVariable Long id) {
        return new ReaderMangaDto(readerService.findReader(id));
    }

    @GetMapping
    public List<ReaderMangaDto> getReaders() {
        return readerService.findAllReaders().stream()
                .map(ReaderMangaDto::new)
                .toList();
    }

    @PostMapping
    public ReaderMangaDto createReader(@RequestParam("readerName") String readerName,
                                       @RequestParam("password") String password) {
        return new ReaderMangaDto(readerService.addReader(readerName, password));
    }

    @PutMapping("/{id}")
    public ReaderMangaDto updateReader(@PathVariable Long id,
                                       @RequestParam("readerName") String readerName,
                                       @RequestParam("password") String password) {
        return new ReaderMangaDto(readerService.updateReader(id, readerName, password));
    }

    @PutMapping("/{id}/addManga")
    public MangaDto addManga(@PathVariable Long id,
                             @RequestParam("mangaId") Long mangaId) {
        return new MangaDto(mangaService.addMangaToReader(mangaId, id));
    }

    @PutMapping("/{id}/removeManga")
    public MangaDto removeManga(@PathVariable Long id,
                                      @RequestParam("mangaId") Long mangaId) {
        return new MangaDto(mangaService.removeMangaToReader(mangaId, id));
    }

    @DeleteMapping("/{id}")
    public ReaderMangaDto deleteReader(@PathVariable Long id) {
        return new ReaderMangaDto(readerService.deleteReader(id));
    }
}
