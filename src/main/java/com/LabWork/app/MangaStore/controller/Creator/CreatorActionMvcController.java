package com.LabWork.app.MangaStore.controller.Creator;

import com.LabWork.app.MangaStore.model.Dto.CreatorMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.CreatorService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Controller
@RequestMapping("/creatorAction")
@Secured({UserRole.AsString.ADMIN})
public class CreatorActionMvcController {
    private final CreatorService creatorService;
    private static final Logger log = LoggerFactory.getLogger(CreatorActionMvcController.class);
    private final MangaService mangaService;

    public CreatorActionMvcController(CreatorService creatorService, MangaService mangaService) {
        this.creatorService = creatorService;
        this.mangaService = mangaService;
    }

    @GetMapping()
    public String getCreator(Model model, Principal principal) {
        model.addAttribute("creators",
                creatorService.findAllCreators().stream()
                        .map(CreatorMangaDto::new)
                        .toList());
        CreatorMangaDto currentCreator = new CreatorMangaDto(creatorService.findByLogin(principal.getName()));
        model.addAttribute("currentCreator", currentCreator);
        return "creatorAction";
    }

    @GetMapping("/edit/{id}")
    public String editManga(@PathVariable Long id, Model model, Principal principal) {
        if (principal.getName().equals(principal.getName())) {
            model.addAttribute("Id", id);
            model.addAttribute("mangaDto", new MangaDto(mangaService.findManga(id)));
            model.addAttribute("controller", "manga/");
            return "creatorAction-edit";
        }
        return "creatorAction";
    }

    @GetMapping("/create")
    public String createManga(Model model, Principal principal) {
        model.addAttribute("mangaDto", new MangaDto());
        model.addAttribute("controller", "creator/");
        return "creatorAction-edit";
    }

    @PostMapping( "/creator")
    public String saveManga(@RequestParam("multipartFile") MultipartFile multipartFile,
                            @ModelAttribute @Valid MangaDto mangaDto,
                            BindingResult bindingResult,
                            Model model,
                            Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "creatorAction-edit";
        }
        mangaDto.setImage("data:" + multipartFile.getContentType() + ";base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        mangaDto.setLogin(principal.getName());
        mangaService.addManga(mangaDto);
        return "redirect:/creatorAction";
    }

    @PostMapping( "/manga/{mangaId}")
    public String updateManga(@PathVariable(value = "mangaId", required = false) Long mangaId, @RequestParam("multipartFile") MultipartFile multipartFile,
                              @ModelAttribute @Valid MangaDto mangaDto,
                              BindingResult bindingResult,
                              Model model,
                              Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "creatorAction-edit";
        }
        mangaDto.setImage("data:" + multipartFile.getContentType() + ";base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        mangaService.updateManga(mangaId, mangaDto.getChapterCount(), mangaDto.getImage());
        return "redirect:/creatorAction";
    }

    @PostMapping("/delete/{mangaId}")
    public String deleteCreator(@PathVariable Long mangaId, Principal principal) {
        Long creatorId = mangaService.findManga(mangaId).getCreatorId();
        mangaService.deleteManga(mangaId);
        if (creatorId != null){
            return "redirect:/creatorAction";
        } else {
            return "redirect:/creatorAction";
        }
    }
}
