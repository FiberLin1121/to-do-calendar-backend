package com.fiber.todocalendar.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "habits")
public class Habits {
    private String id;
    private String userId;
    private List<Habit> habitList = new ArrayList<>();
    private long createdTime;
    private long lastModifiedTime;

    public Habits(String userId) {
        long now = System.currentTimeMillis();
        this.userId = userId;
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}
