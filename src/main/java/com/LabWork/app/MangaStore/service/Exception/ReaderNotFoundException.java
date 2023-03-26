package com.LabWork.app.MangaStore.service.Exception;

public class ReaderNotFoundException extends RuntimeException {
    public ReaderNotFoundException(Long id) {
        super(String.format("Reader with id [%s] is not found", id));
    }
}
