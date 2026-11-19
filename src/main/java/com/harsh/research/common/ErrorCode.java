package com.harsh.research.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {

    VALIDATION_FAILED(HttpStatus.UNPROCESSABLE_CONTENT),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    FORBIDDEN(HttpStatus.FORBIDDEN),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    RATE_LIMITED(HttpStatus.TOO_MANY_REQUESTS),
    FILE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE);

    private final HttpStatus status;

    ErrorCode(HttpStatus httpStatus){
        this.status = httpStatus;
    }

    public HttpStatus status(){
        return status;
    }
}
