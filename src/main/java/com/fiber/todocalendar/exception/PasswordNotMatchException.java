package com.fiber.todocalendar.exception;

public class PasswordNotMatchException extends ServiceExcetion{
    public PasswordNotMatchException(String errorMessage) {
        super(errorMessage);
    }
}
