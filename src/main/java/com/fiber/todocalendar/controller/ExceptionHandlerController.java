package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.exception.AccountDuplicateException;
import com.fiber.todocalendar.exception.ServiceExcetion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ServiceExcetion.class)
    public ResponseEntity<String> handle(ServiceExcetion e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrorMessage());
    }

    @ExceptionHandler(AccountDuplicateException.class)
    public ResponseEntity<String> handle(AccountDuplicateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getErrorMessage());
    }
}
