package com.fiber.todocalendar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "todoLists")
public class TodoList {
    @Id
    private String id;
    private String userId;
    private String date;
    private List<Task> todoList = new ArrayList<>();
    private List<Task> doneList = new ArrayList<>();
    private Date createdTime;
    private Date lastModifiedTime;

    public TodoList(String userId, String date) {
        Date now = new Date();
        this.userId = userId;
        this.date = date;
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}
