package com.LabWork.app.MangaStore.controller;


import com.LabWork.app.MangaStore.model.Dto.MangaReaderDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.MangaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manga")
public class MangaController {
    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping("/{id}")
    public MangaReaderDto getManga(@PathVariable Long id) {
        return new MangaReaderDto(mangaService.findManga(id), mangaService.getReader(id));
    }

    @GetMapping
    public List<MangaReaderDto> getMangas() {
        return mangaService.findAllMangas().stream()
                .map(x -> new MangaReaderDto(x, mangaService.getReader(x.getId())))
                .toList();
    }

    @PostMapping
    public MangaReaderDto createManga(@RequestParam("creatorId") Long creatorId,
                                      @RequestParam("chapterCount") Integer chapterCount,
                                      @RequestParam("mangaName") String mangaName) {
        return new MangaReaderDto(mangaService.addManga(creatorId, chapterCount, mangaName), mangaService.getReader(creatorId));
    }

    @PutMapping("/{id}")
    public MangaReaderDto updateManga(@PathVariable Long id,
                                      @RequestParam("chapterCount") Integer chapterCount) {
        return new MangaReaderDto(mangaService.updateManga(id, chapterCount), mangaService.getReader(id));
    }

    @DeleteMapping("/{id}")
    public MangaDto deleteManga(@PathVariable Long id) {
        return new MangaDto(mangaService.deleteManga(id));
    }
}
