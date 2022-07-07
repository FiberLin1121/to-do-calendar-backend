package com.fiber.todocalendar.dto;

import com.fiber.todocalendar.model.LabelSetting;
import com.fiber.todocalendar.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserLoginResponse {
    private String id;
    private String name;
    private String email;
    private String token;
    private LabelSetting labelSetting;
    private Date createdTime;
    private Date lastModifiedTime;

    public UserLoginResponse(User user, String token) {
        this.id= user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.token = token;
        this.labelSetting = user.getLabelSetting();
        this.createdTime = user.getCreatedTime();
        this.lastModifiedTime = user.getLastModifiedTime();
    }
}
