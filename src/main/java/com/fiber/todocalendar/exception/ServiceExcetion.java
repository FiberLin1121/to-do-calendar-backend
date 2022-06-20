package com.fiber.todocalendar.exception;

import lombok.Data;

@Data
public class ServiceExcetion extends RuntimeException {
    private String errorMessage;

    public ServiceExcetion(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
