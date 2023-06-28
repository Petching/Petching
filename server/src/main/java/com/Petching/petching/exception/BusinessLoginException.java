package com.Petching.petching.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BusinessLoginException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public BusinessLoginException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
    public HttpStatus getHttpStatus() {
        return exceptionCode.getStatus();
    }
    public int getHttpStatusCode() {
        return exceptionCode.getStatus().value();
    }
}
