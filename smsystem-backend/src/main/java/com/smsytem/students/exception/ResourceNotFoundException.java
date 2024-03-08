package com.smsytem.students.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private ErrorResponse errorResponse;

    public ResourceNotFoundException(String message) {
        super(message);
        this.errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, message);
    }
}
