package com.fiber.todocalendar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "habits")
public class Habits {
    @Id
    private String id;
    private String userId;
    private List<Habit> habitList = new ArrayList<>();
    private Date createdTime;
    private Date lastModifiedTime;

    public Habits(String userId) {
        Date now = new Date();
        this.userId = userId;
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}
