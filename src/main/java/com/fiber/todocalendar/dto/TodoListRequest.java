package com.fiber.todocalendar.dto;

import com.fiber.todocalendar.model.Task;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel(description = "")
@Data
public class TodoListRequest {
    @NotBlank
    @ApiModelProperty(value = "代辦事項列表ID", required = true)
    private String todoListId;

    @ApiModelProperty(value = "代辦事項", required = true)
    private List<Task> todoList;

    @ApiModelProperty(value = "完成事項", required = true)
    private  List<Task> doneList;
}
