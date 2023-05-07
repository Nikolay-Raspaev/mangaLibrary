package com.LabWork.app.MangaStore.controller;


import com.LabWork.app.MangaStore.configuration.OpenAPI30Configuration;
import com.LabWork.app.MangaStore.model.Default.UserRole;
import com.LabWork.app.MangaStore.model.Dto.ReaderMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.ReaderService;
import com.LabWork.app.MangaStore.service.UserService;
import com.LabWork.app.MangaStore.util.validation.ValidationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(OpenAPI30Configuration.API_PREFIX  + "/reader")
public class ReaderController {
    private final ReaderService readerService;

    private final UserService userService;

    public ReaderController(ReaderService readerService,
                            UserService userService) {
        this.readerService = readerService;
        this.userService = userService;
    }

    @GetMapping("/{login}")
    public ReaderMangaDto getReader(@PathVariable String login) {
        return new ReaderMangaDto(readerService.findReader(userService.findByLogin(login).getReader().getId()));
    }

    @GetMapping
    public List<ReaderMangaDto> getReaders() {
        return readerService.findAllReaders().stream()
                .map(ReaderMangaDto::new)
                .toList();
    }

/*    @PostMapping
    @Secured({UserRole.AsString.ADMIN})
    public String createReader(@RequestParam("readerName") String readerName,
                                       @RequestParam("password") String password) {
        try {
            return new ReaderMangaDto(readerService.addReader(readerName, password)).toString();
        } catch (ValidationException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public ReaderMangaDto updateReader(@PathVariable Long id,
                                       @RequestParam("readerName") String readerName,
                                       @RequestParam("password") String password) {
        return new ReaderMangaDto(readerService.updateReader(id, readerName, password));
    }*/

    @PutMapping("/{id}/addManga")
    public MangaDto addManga(@PathVariable Long id,
                             @RequestParam("mangaId") Long mangaId) {
        return new MangaDto(readerService.addManga(mangaId, id));
    }

    @PutMapping("/{id}/removeManga")
    public MangaDto removeManga(@PathVariable Long id,
                                      @RequestParam("mangaId") Long mangaId) {
        return new MangaDto(readerService.removeManga(mangaId, id));
    }

    @DeleteMapping("/{id}")
    public ReaderMangaDto deleteReader(@PathVariable Long id) {
        return new ReaderMangaDto(readerService.deleteReader(id));
    }
}
