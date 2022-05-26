package com.fiber.todocalendar.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Habit {
    private String habitId;
    private String name;
    private String checkColor;
    private long createdTime;
    private long lastModifiedTime;
}
