package com.LabWork.app.MangaStore.controller;

import com.LabWork.app.MangaStore.model.Dto.CreatorMangaDto;
import com.LabWork.app.MangaStore.service.CreatorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creator")
public class CreatorController {
    private final CreatorService creatorService;

    public CreatorController(CreatorService creatorService) {
        this.creatorService = creatorService;
    }

    @GetMapping("/{id}")
    public CreatorMangaDto getCreator(@PathVariable Long id) {
        return new CreatorMangaDto(creatorService.findCreator(id));
    }

    @GetMapping
    public List<CreatorMangaDto> getCreators() {
        return creatorService.findAllCreators().stream()
                .map(CreatorMangaDto::new)
                .toList();
    }

    @PostMapping
    public CreatorMangaDto createCreator(@RequestParam("creatorName") String creatorName,
                                         @RequestParam("password") String password) {
        return new CreatorMangaDto(creatorService.addCreator(creatorName, password));
    }

    @PutMapping("/{id}")
    public CreatorMangaDto updateCreator(@PathVariable Long id,
                                         @RequestParam("creatorName") String creatorName,
                                         @RequestParam("password") String password) {
        return new CreatorMangaDto(creatorService.updateCreator(id, creatorName, password));
    }

    @DeleteMapping("/{id}")
    public CreatorMangaDto deleteCreator(@PathVariable Long id) {
        //creatorService.deleteAllCreators();
        return new CreatorMangaDto(creatorService.deleteCreator(id));
    }

    @DeleteMapping
    public void deleteAllCreator() {
        creatorService.deleteAllCreators();
    }
}
