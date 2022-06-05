package com.fiber.todocalendar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "habitTrackers")
public class HabitTracker {
    @Id
    private String id;
    private String habitId;
    private String year;
    private List<Map> pickedDays = new ArrayList<>();
    private long createdTime;
    private long lastModifiedTime;

    public HabitTracker(String habitId, String year) {
        long now = System.currentTimeMillis();
        this.habitId = habitId;
        this.year = year;
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}