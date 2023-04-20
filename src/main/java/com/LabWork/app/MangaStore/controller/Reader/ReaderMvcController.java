package com.LabWork.app.MangaStore.controller.Reader;

import com.LabWork.app.MangaStore.model.Dto.ReaderMangaDto;
import com.LabWork.app.MangaStore.service.ReaderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reader")
public class ReaderMvcController {
    private final ReaderService readerService;

    public ReaderMvcController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public String getReaders(Model model) {
        model.addAttribute("readers",
                readerService.findAllReaders().stream()
                        .map(ReaderMangaDto::new)
                        .toList());
        return "reader";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editReader(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("ReaderMangaDto", new ReaderMangaDto());
        } else {
            model.addAttribute("readerId", id);
            model.addAttribute("ReaderMangaDto", new ReaderMangaDto(readerService.findReader(id)));
        }
        return "reader-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveReader(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid ReaderMangaDto readerMangaDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "reader-edit";
        }
        if (id == null || id <= 0) {
            readerService.addReader(readerMangaDto.getReaderName(), readerMangaDto.getHashedPassword());
        } else {
            readerService.updateReader(id, readerMangaDto.getReaderName(), readerMangaDto.getHashedPassword());
        }
        return "redirect:/reader";
    }

    @PostMapping("/delete/{id}")
    public String deleteReader(@PathVariable Long id) {
        readerService.deleteReader(id);
        return "redirect:/reader";
    }
}
