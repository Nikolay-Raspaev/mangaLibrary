package com.LabWork.app.MangaStore.controller;


import com.LabWork.app.MangaStore.model.Dto.MangaReaderDto;
import com.LabWork.app.MangaStore.model.Dto.MangaUserDto;
import com.LabWork.app.MangaStore.model.Dto.ReaderMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.MangaService;
import com.LabWork.app.MangaStore.configuration.OpenAPI30Configuration;
import com.LabWork.app.MangaStore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(OpenAPI30Configuration.API_PREFIX + "/manga")
public class MangaController {
    private final MangaService mangaService;

    private final UserService userService;

    public MangaController(MangaService mangaService,
                           UserService userService) {
        this.mangaService = mangaService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public MangaUserDto getManga(@PathVariable Long id) {
        return new MangaUserDto(mangaService.findManga(id), mangaService.getReader(id).stream()
                .map(x -> userService.findUser(x.getId()))
                .toList());
    }

    @GetMapping
    public List<MangaReaderDto> getMangas() {
        return mangaService.findAllMangas().stream()
                .map(x -> new MangaReaderDto(x, mangaService.getReader(x.getId())))
                .toList();
    }

    @GetMapping("/{id}/readers")
    public List<ReaderMangaDto> getReaders(@PathVariable Long id) {
        return mangaService.getReader(id).stream()
                .map(x -> new ReaderMangaDto(x))
                .toList();
    }

    @DeleteMapping("/{id}")
    public MangaDto deleteManga(@PathVariable Long id) {
        return new MangaDto(mangaService.deleteManga(id));
    }

    @PostMapping
    public MangaDto createManga(@RequestBody @Valid MangaDto mangaDto) {
        return new MangaDto(mangaService.addManga(mangaDto));
    }

    @PutMapping("/{id}")
    public MangaDto updateManga(@RequestBody @Valid MangaDto mangaDto) {
        return new MangaDto(mangaService.updateManga(mangaDto));
    }
}
