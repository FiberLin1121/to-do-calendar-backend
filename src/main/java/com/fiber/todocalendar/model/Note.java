package com.fiber.todocalendar.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Note {
    private String noteId;
    private String name;
    private String content;
    private String bgColor;
    private String tapeStyle;

    public Note(String name, String content, String bgColor, String tapeStyle) {
        this.noteId = UUID.randomUUID().toString().toLowerCase().replace("-","");
        this.name = name;
        this.content = content;
        this.bgColor = bgColor;
        this.tapeStyle = tapeStyle;
    }
}
