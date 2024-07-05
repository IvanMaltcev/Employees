package com.sky.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeIsNotValidException extends RuntimeException {

    public EmployeeIsNotValidException(String message) {
        super(message);
    }
}
