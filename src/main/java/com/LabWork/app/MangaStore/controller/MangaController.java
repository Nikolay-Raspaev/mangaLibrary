package com.LabWork.app.MangaStore.controller;


import com.LabWork.app.MangaStore.model.Default.Creator;
import com.LabWork.app.MangaStore.model.Dto.MangaDto;
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
    public MangaDto getManga(@PathVariable Long id) {
        return new MangaDto(mangaService.findManga(id));
    }

    @GetMapping
    public List<MangaDto> getMangas() {
        return mangaService.findAllMangas().stream()
                .map(MangaDto::new)
                .toList();
    }

    @PostMapping
    public MangaDto createManga(@RequestParam("creatorId") Long creatorId,
                                @RequestParam("chapterCount") Integer chapterCount,
                                @RequestParam("mangaName") String mangaName) {
        return new MangaDto(mangaService.addManga(creatorId, chapterCount, mangaName));
    }

    @PutMapping("/{id}")
    public MangaDto updateManga(@PathVariable Long id,
                                   @RequestParam("chapterCount") Integer chapterCount) {
        return new MangaDto(mangaService.updateManga(id, chapterCount));
    }

    @DeleteMapping("/{id}")
    public MangaDto deleteManga(@PathVariable Long id) {
        return new MangaDto(mangaService.deleteManga(id));
    }
}
