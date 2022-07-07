package com.fiber.todocalendar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private LabelSetting labelSetting;
    private Date createdTime;
    private Date lastModifiedTime;

    public User(String name, String email, String password) {
        Date now = new Date();
        this.name = name;
        this.email = email;
        this.password = password;
        this.labelSetting = new LabelSetting();
        this.createdTime = now;
        this.lastModifiedTime = now;
    }
}
