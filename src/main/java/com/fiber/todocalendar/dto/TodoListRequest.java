package com.fiber.todocalendar.dto;

import com.fiber.todocalendar.model.Task;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class TodoListRequest {
    @NotBlank
    private String todoListId;
    private List<Task> todoList;
    private  List<Task> doneList;
}
