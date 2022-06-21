package com.fiber.todocalendar.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "notes")
public class Notes {
    @Id
    private String id;
    private String userId;
    private List<Note> list1 = new ArrayList();
    private List<Note> list2 = new ArrayList();
    private List<Note> list3 = new ArrayList();
    private List<Note> list4 = new ArrayList();
    private Date createdTime;
    private Date lastModifiedTime;

    public Notes(String userId) {
        Date now = new Date();
        this.userId = userId;
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}
