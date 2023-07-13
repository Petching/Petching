package com.Petching.petching.global.exception;

import lombok.Getter;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ExceptionCode {

    BAD_REQUEST(400,"Invalid request. please try again."),
    USER_EXIST(400, "중복된 이메일입니다."),
    NICKNAME_EXIST(400, "중복된 닉네임입니다."),
    UNAUTHORIZED(401,"Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    MEMBER_NOT_FOUND(404, "Member Not Found, 해당하는 멤버를 찾을 수 없습니다."),
    CONTENT_NOT_FOUND(404,"Content Not Found"),
    METHOD_NOT_ALLOWED(405,"Method Not Allowed, 해당하는 요청은 허용되지 않습니다."),
    TIME_OUT(408, "Time-out"),
    MEMBER_EXISTS(409, "Member Exists, 이미 존재하는 멤버입니다."),
    UNSUPPORTED_MEDIA_TYPE(415,"Unsupported Media Type, 허용되지 않는 데이터 타입입니다."),
    COMMENT_NOT_FOUND(404, "Comment not found"),
    POST_NOT_WRITE(401, "Post not write"),
    POST_NOT_FOUND(404,"Post not found"),
    NOT_FOUND(500, "The Requested Access Is Invalid."),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Temporarily Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Time-out"),
    UNKNOWN_ERROR(520, "Unknown Error");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }

}
