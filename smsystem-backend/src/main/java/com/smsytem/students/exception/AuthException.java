package com.smsytem.students.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException {
    private ErrorResponse errorResponse;

    public AuthException(String message) {
        super(message);
        this.errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "You are not authorized to access this resource");
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
