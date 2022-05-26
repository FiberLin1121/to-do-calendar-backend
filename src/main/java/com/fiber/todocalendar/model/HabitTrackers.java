package com.fiber.todocalendar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "habitTrackers")
public class HabitTrackers {
    @Id
    private String id;
    private String year;
    private List<String> pickedDays = new ArrayList<>();
    private long createdTime;
    private long lastModifiedTime;
}