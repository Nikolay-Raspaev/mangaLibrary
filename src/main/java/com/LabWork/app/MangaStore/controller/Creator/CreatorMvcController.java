package com.LabWork.app.MangaStore.controller.Creator;

import com.LabWork.app.MangaStore.model.Dto.CreatorMangaDto;
import com.LabWork.app.MangaStore.service.CreatorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/creator")
public class CreatorMvcController {
    private final CreatorService creatorService;

    public CreatorMvcController(CreatorService creatorService) {
        this.creatorService = creatorService;
    }

    @GetMapping
    public String getCreators(Model model) {
        model.addAttribute("creators",
                creatorService.findAllCreators().stream()
                        .map(CreatorMangaDto::new)
                        .toList());
        return "creator";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editCreator(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("CreatorMangaDto", new CreatorMangaDto());
        } else {
            model.addAttribute("creatorId", id);
            model.addAttribute("CreatorMangaDto", new CreatorMangaDto(creatorService.findCreator(id)));
        }
        return "creator-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveCreator(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid CreatorMangaDto creatorMangaDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "creator-edit";
        }
        if (id == null || id <= 0) {
            creatorService.addCreator(creatorMangaDto.getCreatorName(), creatorMangaDto.getHashedPassword());
        } else {
            creatorService.updateCreator(id, creatorMangaDto.getCreatorName(), creatorMangaDto.getHashedPassword());
        }
        return "redirect:/creator";
    }

    @PostMapping("/delete/{id}")
    public String deleteCreator(@PathVariable Long id) {
        creatorService.deleteCreator(id);
        return "redirect:/creator";
    }
}
