package com.fiber.todocalendar.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Habit {
    private String habitId;
    private String name;
    private String checkColor;
    private long createdTime;
    private long lastModifiedTime;

    public Habit(String name, String checkColor) {
        long now = System.currentTimeMillis();
        this.habitId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        this.name = name;
        this.checkColor = checkColor;
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}
