package com.Petching.petching.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ExceptionCode {
    USER_EXIST(BAD_REQUEST, "중복된 이메일입니다."),
    NICKNAME_EXIST(BAD_REQUEST, "중복된 닉네임입니다."),
    USER_NOT_FOUND(NOT_FOUND, "회원이 없습니다."),
    BOARD_NOT_FOUND(NOT_FOUND,"Board not found"),
    COMMENT_NOT_FOUND(NOT_FOUND, "Comment not found"),
    POST_NOT_WRITE(UNAUTHORIZED, "Post not write"),
    POST_NOT_FOUND(NOT_FOUND,"Post not found"),

    CONTENT_NOT_FOUND(NOT_FOUND, "Content not found");
  
    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
