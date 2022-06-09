package com.fiber.todocalendar.dto;

import com.fiber.todocalendar.model.Habit;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class HabitsRequest {
    @NotBlank
    private String habitsId;
    @NotEmpty
    private List<Habit> habitList = new ArrayList<>();
}
