package com.fiber.todocalendar.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@ApiModel(description = "")
@Data
public class UserPatchRequest {
    @ApiModelProperty(value = "操作方式", required = true, example = "replace")
    @NotBlank
    private String op;

    @ApiModelProperty(value = "目標欄位", required = true, example = "/labelSetting")
    @NotBlank
    private String path;

    @ApiModelProperty(value = "值")
    private Map value;
}
