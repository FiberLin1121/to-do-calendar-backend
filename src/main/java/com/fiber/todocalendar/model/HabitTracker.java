package com.fiber.todocalendar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document(collection = "habitTrackers")
public class HabitTracker {
    @Id
    private String id;
    private String habitId;
    private String year;
    private List<Map> pickedDays = new ArrayList<>();
    private Date createdTime;
    private Date lastModifiedTime;

    public HabitTracker(String habitId, String year) {
        Date now = new Date();
        this.habitId = habitId;
        this.year = year;
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}