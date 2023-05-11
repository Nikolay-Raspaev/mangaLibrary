package com.LabWork.app.MangaStore.controller.Reader;

import com.LabWork.app.MangaStore.model.Dto.CreatorMangaDto;
import com.LabWork.app.MangaStore.model.Dto.ReaderMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.ReaderService;
import com.LabWork.app.MangaStore.service.MangaService;
import jakarta.validation.Valid;

import com.LabWork.app.MangaStore.model.Default.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/readerAction")
@Secured({UserRole.AsString.USER})
public class ReaderActionMvcController {
    private final ReaderService readerService;
    private static final Logger log = LoggerFactory.getLogger(ReaderActionMvcController.class);
    private final MangaService mangaService;

    public ReaderActionMvcController(ReaderService readerService, MangaService mangaService) {
        this.readerService = readerService;
        this.mangaService = mangaService;
    }

    @GetMapping()
    public String getReader(Model model, Principal principal) {
        model.addAttribute("readers",
                readerService.findAllReaders().stream()
                        .map(ReaderMangaDto::new)
                        .toList());
        ReaderMangaDto currentReader = new ReaderMangaDto(readerService.findByLogin(principal.getName()));
        model.addAttribute("readerId", currentReader.getId());
        model.addAttribute("reader", new ReaderMangaDto(readerService.findReader(currentReader.getId())));
        model.addAttribute("MangaDto", new MangaDto());
        model.addAttribute("mangaList", mangaService.findAllMangas());
        return "readerAction";
    }

/*    @GetMapping()
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
    }*/

    @PostMapping("/manga")
    public String saveManga(@RequestParam("mangaId") Long mangaId,
                            @ModelAttribute @Valid MangaDto MangaDto,
                            BindingResult bindingResult,
                            Model model,
                            Principal principal){
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "readerAction";
        }
        readerService.addManga(mangaId, principal.getName());
        return "redirect:/readerAction";
    }

    @PostMapping("/removeManga/{mangaId}")
    public String removeManga(@PathVariable Long mangaId, Principal principal) {
        readerService.removeManga(mangaId, principal.getName());
        return "redirect:/readerAction/?readerLogin=" + principal.getName();
    }
}
