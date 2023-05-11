package com.LabWork.app.MangaStore.controller.Manga;

import com.LabWork.app.MangaStore.model.Dto.MangaReaderDto;
import com.LabWork.app.MangaStore.model.Dto.ReaderMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.MangaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manga")
public class MangaMvcController {
    private final MangaService mangaService;

    public MangaMvcController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping()
    public String getMangaAndReaders(Model model) {
        model.addAttribute("mangaList", mangaService.findAllMangas().stream()
                .map(x -> new MangaDto(x))
                .toList());
        return "catalog";
    }

    @GetMapping("/{id}")
    public String getMangaAndReaders(@PathVariable Long id, Model model) {
        model.addAttribute("manga", new MangaReaderDto(mangaService.findManga(id), mangaService.getReader(id)));
        model.addAttribute("readers", mangaService.getReader(id).stream()
                .map(x -> new ReaderMangaDto(x))
                .toList());
        return "mangaPage";
    }
}
