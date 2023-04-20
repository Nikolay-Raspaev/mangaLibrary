package com.LabWork.app.MangaStore.controller;

import com.LabWork.app.MangaStore.model.Dto.CreatorMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.CreatorService;
import com.LabWork.app.MangaStore.service.MangaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/creatorAction")
public class CreatorActionMvcController {
    private final CreatorService creatorService;
    private static final Logger log = LoggerFactory.getLogger(CreatorActionMvcController.class);
    private final MangaService mangaService;

    public CreatorActionMvcController(CreatorService creatorService, MangaService mangaService) {
        this.creatorService = creatorService;
        this.mangaService = mangaService;
    }

    @GetMapping
    public String getCreators(Model model) {
        model.addAttribute("creators",
                creatorService.findAllCreators().stream()
                        .map(CreatorMangaDto::new)
                        .toList());
        model.addAttribute("creatorId", 0);
        return "creatorAction";
    }

    @GetMapping("/{id}")
    public String getCreator(@PathVariable Long id, Model model) {
        model.addAttribute("creator", new CreatorMangaDto(creatorService.findCreator(id)));
        return "creatorAction";
    }

    @GetMapping("/edit/{id}")
    public String editManga(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("mangaId", id);
        model.addAttribute("MangaDto", new MangaDto(mangaService.findManga(id)));
        return "creatorAction-edit";
    }

    @GetMapping("/create/{id}")
    public String createManga(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("creatorId", id);
        model.addAttribute("MangaDto", new MangaDto());
        return "creatorAction-edit";
    }

    @PostMapping(value = {"/{creatorId}/manga/", "/{creatorId}/manga/{mangaId}"})
    public String saveManga(@PathVariable Long creatorId, @PathVariable(value = "mangaId", required = false) Long mangaId, @RequestParam("image") MultipartFile multipartFile,
                              @ModelAttribute @Valid MangaDto MangaDto,
                              BindingResult bindingResult,
                              Model model) throws IOException {
/*        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "creatorAction-edit";
        }*/
        MangaDto.setImage(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        log.info(MangaDto.getMangaName());
        MangaDto.setCreatorId(creatorId);
        if (mangaId == null || mangaId <= 0) {
            mangaService.addManga(MangaDto);
        } else {
            mangaService.updateManga(mangaId, MangaDto.getChapterCount(), MangaDto.getImage());
        }
        return "redirect:/creatorAction/1";
    }

    @PostMapping("/delete/{id}")
    public String deleteCreator(@PathVariable Long id) {
        mangaService.deleteManga(id);
        return "redirect:/creatorAction/1";
    }
}
