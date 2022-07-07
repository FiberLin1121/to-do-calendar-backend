package com.fiber.todocalendar.exception;

public class AccountDuplicateException extends ServiceExcetion {
    public AccountDuplicateException(String errorMessage) {
        super(errorMessage);
    }
}
