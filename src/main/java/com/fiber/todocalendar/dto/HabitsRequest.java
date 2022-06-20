package com.fiber.todocalendar.dto;

import com.fiber.todocalendar.model.Habit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel(description = "")
@Data
public class HabitsRequest {
    @ApiModelProperty(value = "原子習慣列表ID", required = true)
    @NotBlank
    private String habitsId;

    @NotEmpty
    @ApiModelProperty(value = "原子習慣列表", required = true)
    private List<Habit> habitList;
}
