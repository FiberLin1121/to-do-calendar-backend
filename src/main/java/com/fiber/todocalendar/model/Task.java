package com.fiber.todocalendar.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Task {
    private String taskId;
    private String name;
    private String labelType;
    private Date createdTime;
    private Date lastModifiedTime;

    public Task(String name, String labelType) {
        Date now = new Date();
        this.taskId = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        this.name = name;
        this.labelType = labelType;
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}
