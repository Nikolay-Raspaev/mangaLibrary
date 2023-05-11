package com.LabWork.app.MangaStore.controller.Creator;

import com.LabWork.app.MangaStore.model.Dto.CreatorMangaDto;
import com.LabWork.app.MangaStore.model.Dto.SupportDto.MangaDto;
import com.LabWork.app.MangaStore.service.CreatorService;
import com.LabWork.app.MangaStore.service.MangaService;
import javax.validation.Valid;

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
    public String getCreator(@RequestParam("login") String login, Model model, Principal principal) {
        if (login.equals(principal.getName())) {
            model.addAttribute("creators",
                    creatorService.findAllCreators().stream()
                            .map(CreatorMangaDto::new)
                            .toList());
            CreatorMangaDto currentCreator = new CreatorMangaDto(creatorService.findByLogin(login));
            model.addAttribute("currentCreator", currentCreator);
            model.addAttribute("login", login);
            return "creatorAction";
        }
        return "creatorAction";
    }

    @GetMapping("/edit/{id}/{login}")
    public String editManga(@PathVariable Long id, @PathVariable String login, Model model, Principal principal) {
        if (login.equals(principal.getName())) {
            model.addAttribute("Id", id);
            model.addAttribute("mangaDto", new MangaDto(mangaService.findManga(id)));
            model.addAttribute("controller", "manga/");
            return "creatorAction-edit";
        }
        return "creatorAction";
    }

    @GetMapping("/create/{login}")
    public String createManga(@PathVariable String login, Model model, Principal principal) {
        if (login.equals(principal.getName())) {
            model.addAttribute("login", login);
            model.addAttribute("mangaDto", new MangaDto());
            model.addAttribute("controller", "creator/");
            return "creatorAction-edit";
        }
        return "creatorAction";
    }

    @PostMapping( "/creator/{login}")
    public String saveManga(@PathVariable(value = "login", required = false) String login,
                            @RequestParam("multipartFile") MultipartFile multipartFile,
                            @ModelAttribute @Valid MangaDto mangaDto,
                            BindingResult bindingResult,
                            Model model,
                            Principal principal) throws IOException {
        if (login.equals(principal.getName())) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getAllErrors());
                return "creatorAction-edit";
            }
            mangaDto.setImage("data:" + multipartFile.getContentType() + ";base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
            mangaDto.setLogin(login);
            mangaService.addManga(mangaDto);
            return "redirect:/creatorAction?login=" + login;
        }
        return "creatorAction";
    }

    @PostMapping( "/manga/{mangaId}/{login}")
    public String updateManga(@PathVariable(value = "mangaId", required = false) Long mangaId, @PathVariable(value = "login", required = false) String login, @RequestParam("multipartFile") MultipartFile multipartFile,
                              @ModelAttribute @Valid MangaDto mangaDto,
                              BindingResult bindingResult,
                              Model model,
                              Principal principal) throws IOException {
        if (login.equals(principal.getName())) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getAllErrors());
                return "creatorAction-edit";
            }
            mangaDto.setImage("data:" + multipartFile.getContentType() + ";base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
            mangaService.updateManga(mangaId, mangaDto.getChapterCount(), mangaDto.getImage());
            return "redirect:/creatorAction?login=" + login;
        }
        return "creatorAction";
    }

    @PostMapping("/delete/{mangaId}/{login}")
    public String deleteCreator(@PathVariable Long mangaId, @PathVariable String login,Principal principal) {
        if (login.equals(principal.getName())) {
            Long creatorId = mangaService.findManga(mangaId).getCreatorId();
            mangaService.deleteManga(mangaId);
            if (creatorId != null){
                return "redirect:/creatorAction?login=" + login;
            } else {
                return "redirect:/creatorAction";
            }
        }
        return "creatorAction";
    }
}
