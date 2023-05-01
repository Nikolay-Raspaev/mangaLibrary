package com.LabWork.app.MangaStore.controller.Reader;

import com.LabWork.app.MangaStore.model.Dto.CreatorMangaDto;
import com.LabWork.app.MangaStore.model.Dto.ReaderMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.ReaderService;
import com.LabWork.app.MangaStore.service.MangaService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @GetMapping("/{user}")
    public String getReader(@PathVariable String user, Model model) {
        model.addAttribute("readers",
                readerService.findAllReaders().stream()
                        .map(ReaderMangaDto::new)
                        .toList());
        ReaderMangaDto currentReader = new ReaderMangaDto(readerService.findReader(readerService.findByLogin(user).getId()));
        model.addAttribute("readerId", currentReader.getId());
        model.addAttribute("reader", new ReaderMangaDto(readerService.findReader(currentReader.getId())));
        model.addAttribute("MangaDto", new MangaDto());
        model.addAttribute("mangaList", mangaService.findAllMangas());
        return "readerAction";
    }

    @GetMapping()
    public String getReader(@RequestParam(value = "readerId", required = false) Long readerId, Model model) {
        model.addAttribute("readers",
                readerService.findAllReaders().stream()
                        .map(ReaderMangaDto::new)
                        .toList());
        if (readerId != null){
            model.addAttribute("readerId", readerId);
            model.addAttribute("reader", new ReaderMangaDto(readerService.findReader(readerId)));
        } else {
            model.addAttribute("readerId", 0);
            model.addAttribute("reader", new ReaderMangaDto());
        }
        model.addAttribute("MangaDto", new MangaDto());
        model.addAttribute("mangaList", mangaService.findAllMangas());
        return "readerAction";
    }

    @PostMapping("/manga/{readerId}")
    public String saveManga(@PathVariable Long readerId,
                            @RequestParam("mangaId") Long mangaId,
                            @ModelAttribute @Valid MangaDto MangaDto,
                            BindingResult bindingResult,
                            Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "readerAction";
        }
        readerService.addManga(mangaId, readerId);
        return "redirect:/readerAction/?readerId=" + readerId;
    }

    @PostMapping("/{id}/removeManga/{mangaId}")
    public String removeManga(@PathVariable Long id, @PathVariable Long mangaId) {
        readerService.removeManga(mangaId, id);
        return "redirect:/readerAction/?readerId=" + id;
    }
}
