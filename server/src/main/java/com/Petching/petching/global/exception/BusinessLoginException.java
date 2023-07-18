package com.Petching.petching.global.exception;


public class BusinessLoginException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public BusinessLoginException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}