package com.LabWork.app.MangaStore.controller;

import com.LabWork.app.MangaStore.model.Dto.CreatorMangaDto;
import com.LabWork.app.MangaStore.service.CreatorService;
import com.LabWork.app.MangaStore.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.LabWork.app.MangaStore.configuration.OpenAPI30Configuration;
import java.util.List;

@RestController
@RequestMapping(OpenAPI30Configuration.API_PREFIX + "/creator")
public class CreatorController {
    private final CreatorService creatorService;

    private final UserService userService;

    public CreatorController(CreatorService creatorService,
                             UserService userService) {
        this.creatorService = creatorService;
        this.userService = userService;
    }

    @GetMapping("/{login}")
    public CreatorMangaDto getCreator(@PathVariable String login) {
        return new CreatorMangaDto(creatorService.findCreator(userService.findByLogin(login).getCreator().getId()));
    }

    @GetMapping
    public List<CreatorMangaDto> getCreators() {
        return creatorService.findAllCreators().stream()
                .map(CreatorMangaDto::new)
                .toList();
    }

/*    @PostMapping
    public CreatorMangaDto createCreator(@RequestParam("creatorName") String creatorName,
                                         @RequestParam("password") String password) {
        return new CreatorMangaDto(creatorService.addCreator());
    }*/

/*    @PutMapping("/{id}")
    public CreatorMangaDto updateCreator(@PathVariable Long id,
                                         @RequestParam("creatorName") String creatorName,
                                         @RequestParam("password") String password) {
        return new CreatorMangaDto(creatorService.updateCreator(id, creatorName, password));
    }*/

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
