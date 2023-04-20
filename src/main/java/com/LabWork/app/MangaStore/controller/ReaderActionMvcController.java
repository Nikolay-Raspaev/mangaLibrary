package com.LabWork.app.MangaStore.controller;

import com.LabWork.app.MangaStore.model.Dto.ReaderMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.ReaderService;
import com.LabWork.app.MangaStore.service.MangaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/readerAction")
public class ReaderActionMvcController {
    private final ReaderService readerService;
    private static final Logger log = LoggerFactory.getLogger(ReaderActionMvcController.class);
    private final MangaService mangaService;

    public ReaderActionMvcController(ReaderService readerService, MangaService mangaService) {
        this.readerService = readerService;
        this.mangaService = mangaService;
    }

    @GetMapping
    public String getReaders(Model model) {
        model.addAttribute("readers",
                readerService.findAllReaders().stream()
                        .map(ReaderMangaDto::new)
                        .toList());
        model.addAttribute("readerId", 0);
        return "readerAction";
    }

    @GetMapping("/{id}")
    public String getReader(@PathVariable Long id, Model model) {
        model.addAttribute("reader", new ReaderMangaDto(readerService.findReader(id)));
        model.addAttribute("mangaId", id);
        model.addAttribute("MangaDto", new MangaDto());
        model.addAttribute("mangaList", mangaService.findAllMangas());
        return "readerAction";
    }

    @PostMapping("/{readerId}/manga")
    public String saveManga(@PathVariable Long readerId, @RequestParam("mangaId") Long mangaId,
                              @ModelAttribute @Valid MangaDto MangaDto,
                              BindingResult bindingResult,
                              Model model) throws IOException {
/*        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "readerAction-edit";
        }*/
        readerService.addManga(mangaId, readerId);
        return "redirect:/readerAction/1";
    }

    @PostMapping("/{id}/removeManga/{mangaId}")
    public String removeManga(@PathVariable Long id, @PathVariable Long mangaId) {
        readerService.removeManga(mangaId, id);
        return "redirect:/readerAction/1";
    }
}
