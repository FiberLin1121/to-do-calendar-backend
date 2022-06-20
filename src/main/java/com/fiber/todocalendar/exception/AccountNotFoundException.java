package com.fiber.todocalendar.exception;

public class AccountNotFoundException extends ServiceExcetion{
    public AccountNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
