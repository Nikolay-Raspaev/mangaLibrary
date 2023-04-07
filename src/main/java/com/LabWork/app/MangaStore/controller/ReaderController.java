package com.LabWork.app.MangaStore.controller;


import com.LabWork.app.MangaStore.model.Dto.ReaderMangaDto;
import com.LabWork.app.MangaStore.service.ReaderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
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
    public ReaderMangaDto addManga(@PathVariable Long id,
                                   @RequestParam("mangaId") Long mangaId) {
        return new ReaderMangaDto(readerService.addManga(mangaId, id));
    }

    @PutMapping("/{id}/removeManga")
    public ReaderMangaDto removeManga(@PathVariable Long id,
                                      @RequestParam("mangaId") Long mangaId) {
        return new ReaderMangaDto(readerService.removeManga(mangaId, id));
    }

    @DeleteMapping("/{id}")
    public ReaderMangaDto deleteReader(@PathVariable Long id) {
        return new ReaderMangaDto(readerService.deleteReader(id));
    }
}
