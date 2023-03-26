package com.LabWork.app.MangaStore.controller;


import com.LabWork.app.MangaStore.model.Dto.ReaderDto;
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
    public ReaderDto getReader(@PathVariable Long id) {
        return new ReaderDto(readerService.findReader(id));
    }

    @GetMapping
    public List<ReaderDto> getReaders() {
        return readerService.findAllReaders().stream()
                .map(ReaderDto::new)
                .toList();
    }

    @PostMapping
    public ReaderDto createReader(@RequestParam("readerName") String readerName,
                                   @RequestParam("password") String password) {
        return new ReaderDto(readerService.addReader(readerName, password));
    }

    @PutMapping("/{id}")
    public ReaderDto updateReader(@PathVariable Long id,
                                   @RequestParam("readerName") String readerName,
                                   @RequestParam("password") String password) {
        return new ReaderDto(readerService.updateReader(id, readerName, password));
    }

    @PostMapping("/{id}/manga/{manga_id}")
    public ReaderDto addManga(@PathVariable Long id,
                              @PathVariable Long mangaId) {
        return new ReaderDto(readerService.addManga(mangaId, id));
    }

    @PutMapping("/{id}/manga/{manga_id}")
    public ReaderDto removeManga(@PathVariable Long id,
                              @PathVariable Long mangaId) {
        return new ReaderDto(readerService.removeManga(mangaId, id));
    }

    @DeleteMapping("/{id}")
    public ReaderDto deleteReader(@PathVariable Long id) {
        return new ReaderDto(readerService.deleteReader(id));
    }
}
