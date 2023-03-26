package com.LabWork.app.MangaStore.controller;

import com.LabWork.app.MangaStore.model.Dto.CreatorDto;
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
    public CreatorDto getCreator(@PathVariable Long id) {
        return new CreatorDto(creatorService.findCreator(id));
    }

    @GetMapping
    public List<CreatorDto> getCreators() {
        return creatorService.findAllCreators().stream()
                .map(CreatorDto::new)
                .toList();
    }

    @PostMapping
    public CreatorDto createCreator(@RequestParam("creatorName") String creatorName,
                                   @RequestParam("password") String password) {
        return new CreatorDto(creatorService.addCreator(creatorName, password));
    }

    @PutMapping("/{id}")
    public CreatorDto updateCreator(@PathVariable Long id,
                                    @RequestParam("creatorName") String creatorName,
                                    @RequestParam("password") String password) {
        return new CreatorDto(creatorService.updateCreator(id, creatorName, password));
    }

    @DeleteMapping("/{id}")
    public CreatorDto deleteCreator(@PathVariable Long id) {
        return new CreatorDto(creatorService.deleteCreator(id));
    }
}
