package com.LabWork.app.MangaStore.model.Dto.SupportDto;

import com.LabWork.app.MangaStore.model.Default.Reader;

import java.util.List;

public class ReaderDto {
    private Long id;

    private String readerName;

    private String hashedPassword;

    public ReaderDto() {
    }

    public ReaderDto(Reader reader) {
        this.id = reader.getId();
    }

    public Long getId() {
        return id;
    }

    public String getReaderName() { return readerName; }

    public String getHashedPassword() { return hashedPassword; }

    public void setrRaderName(String readerName) {
        this.readerName = readerName;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
