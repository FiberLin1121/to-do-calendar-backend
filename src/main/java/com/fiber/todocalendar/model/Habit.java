package com.fiber.todocalendar.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Habit {
    private String habitId;
    private String name;
    private String checkColor;
    private Date createdTime;
    private Date lastModifiedTime;

    public Habit(String name, String checkColor) {
        Date now = new Date();
        this.habitId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        this.name = name;
        this.checkColor = checkColor;
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}
