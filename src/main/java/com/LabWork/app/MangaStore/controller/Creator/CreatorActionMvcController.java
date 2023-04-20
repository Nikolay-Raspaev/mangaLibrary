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
        model.addAttribute("creatorId", 0);
        return "creatorAction";
    }

    @GetMapping("/edit/{id}")
    public String editManga(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("mangaId", id);
        model.addAttribute("MangaDto", new MangaDto(mangaService.findManga(id)));
        model.addAttribute("creators",
                creatorService.findAllCreators().stream()
                        .map(CreatorMangaDto::new)
                        .toList());
        return "creatorAction-edit";
    }

    @GetMapping("/create")
    public String createManga(@RequestParam(value = "creatorId", required = false) Long creatorId, Model model) {
        model.addAttribute("creatorId", 0);
        model.addAttribute("MangaDto", new MangaDto());
        model.addAttribute("creators",
                creatorService.findAllCreators().stream()
                        .map(CreatorMangaDto::new)
                        .toList());
        return "creatorAction-edit";
    }

    @PostMapping(value = {"/manga/", "/manga/{mangaId}"})
    public String saveManga(@PathVariable(value = "mangaId", required = false) Long mangaId, @RequestParam("image") MultipartFile multipartFile,
                              @RequestParam(value = "creatorId", required = false) Long creatorId,
                              @ModelAttribute @Valid MangaDto MangaDto,
                              BindingResult bindingResult,
                              Model model) throws IOException {
/*        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "creatorAction-edit";
        }*/
        MangaDto.setImage("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        log.info(MangaDto.getMangaName());
        MangaDto.setCreatorId(creatorId);
        if (mangaId == null || mangaId <= 0) {
            mangaService.addManga(MangaDto);
        } else {
            mangaService.updateManga(mangaId, MangaDto.getChapterCount(), MangaDto.getImage());
        }
        if (creatorId != null){
            return "redirect:/creatorAction?creatorId=" + creatorId;
        } else {
            return "redirect:/creatorAction?creatorId=" + mangaService.findManga(mangaId).getCreatorId();
        }

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
