package com.fiber.todocalendar.dto;

import com.fiber.todocalendar.model.Note;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class NotesRequest {
    @NotBlank
    private String userId;
    private List<Note> list1;
    private List<Note> list2;
    private List<Note> list3;
    private List<Note> list4;
}
