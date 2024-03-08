package com.smsytem.students.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String status;
    private int statusCode;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = "error";
        this.statusCode = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}