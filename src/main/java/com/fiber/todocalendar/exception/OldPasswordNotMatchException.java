package com.fiber.todocalendar.exception;

public class OldPasswordNotMatchException extends ServiceExcetion {
    public OldPasswordNotMatchException(String errorMessage) {
        super(errorMessage);
    }
}
