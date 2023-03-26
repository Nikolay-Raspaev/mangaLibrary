package com.LabWork.app.MangaStore.service.Exception;

public class MangaNotFoundException extends RuntimeException {
    public MangaNotFoundException(Long id) {
        super(String.format("Manga with id [%s] is not found", id));
    }
}
