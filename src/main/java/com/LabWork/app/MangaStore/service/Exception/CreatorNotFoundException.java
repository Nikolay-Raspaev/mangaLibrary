package com.LabWork.app.MangaStore.service.Exception;

public class CreatorNotFoundException extends RuntimeException {
    public CreatorNotFoundException(Long id) {
        super(String.format("Creator with id [%s] is not found", id));
    }
}
