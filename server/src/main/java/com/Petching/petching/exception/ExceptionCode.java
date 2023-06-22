package com.Petching.petching.exception;

import lombok.Getter;

public enum ExceptionCode {
    USER_EXIST(402 , "중복된 이메일입니다."),
    NICKNAME_EXIST(402, "중복된 닉네임입니다.");

    @Getter
    private int coed;
    @Getter
    private String message;

    ExceptionCode(int coed, String message) {
        this.coed = coed;
        this.message = message;
    }
}
