package com.LabWork.app.MangaStore.controller.Creator;

import com.LabWork.app.MangaStore.model.Dto.CreatorMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.CreatorService;
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
@RequestMapping("/creatorAction")
public class CreatorActionMvcController {
    private final CreatorService creatorService;
    private static final Logger log = LoggerFactory.getLogger(CreatorActionMvcController.class);
    private final MangaService mangaService;

    public CreatorActionMvcController(CreatorService creatorService, MangaService mangaService) {
        this.creatorService = creatorService;
        this.mangaService = mangaService;
    }

    @GetMapping()
    public String getCreator(@RequestParam(value = "creatorId", required = false) Long creatorId, Model model) {
        model.addAttribute("creators",
                creatorService.findAllCreators().stream()
                        .map(CreatorMangaDto::new)
                        .toList());
        if(creatorId != null){
            model.addAttribute("currentCreator", new CreatorMangaDto(creatorService.findCreator(creatorId)));
        }
        model.addAttribute("creatorId", creatorId);
        return "creatorAction";
    }

    @GetMapping("/edit/{id}")
    public String editManga(@PathVariable Long id, Model model) {
        model.addAttribute("Id", id);
        model.addAttribute("mangaDto", new MangaDto(mangaService.findManga(id)));
        model.addAttribute("controller", "manga/");
        return "creatorAction-edit";
    }

    @GetMapping("/create/{id}")
    public String createManga(@PathVariable Long id, Model model) {
        model.addAttribute("Id", id);
        model.addAttribute("mangaDto", new MangaDto());
        model.addAttribute("controller", "creator/");
        return "creatorAction-edit";
    }

    @PostMapping( "/creator/{creatorId}")
    public String saveManga(@PathVariable(value = "creatorId", required = false) Long creatorId,
                            @RequestParam("multipartFile") MultipartFile multipartFile,
                            @ModelAttribute @Valid MangaDto mangaDto,
                            BindingResult bindingResult,
                            Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "creatorAction-edit";
        }
        mangaDto.setImage("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        mangaDto.setCreatorId(creatorId);
        mangaService.addManga(mangaDto);
        return "redirect:/creatorAction?creatorId=" + creatorId;

    }

    @PostMapping( "/manga/{mangaId}")
    public String updateManga(@PathVariable(value = "mangaId", required = false) Long mangaId, @RequestParam("multipartFile") MultipartFile multipartFile,
                            @ModelAttribute @Valid MangaDto mangaDto,
                            BindingResult bindingResult,
                            Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "creatorAction-edit";
        }
        mangaDto.setImage("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        mangaService.updateManga(mangaId, mangaDto.getChapterCount(), mangaDto.getImage());
        return "redirect:/creatorAction?creatorId=" + mangaService.findManga(mangaId).getCreatorId();
    }

    @PostMapping("/delete/{id}")
    public String deleteCreator(@PathVariable Long id) {
        Long creatorId = mangaService.findManga(id).getCreatorId();
        mangaService.deleteManga(id);
        if (creatorId != null){
            return "redirect:/creatorAction?creatorId=" + creatorId;
        } else {
            return "redirect:/creatorAction";
        }
    }
}
