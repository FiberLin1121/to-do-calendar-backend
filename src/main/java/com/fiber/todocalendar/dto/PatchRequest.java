package com.fiber.todocalendar.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
public class PatchRequest {
    @NotBlank
    private String op;
    @NotBlank
    private String path;
    private Map value;
}
