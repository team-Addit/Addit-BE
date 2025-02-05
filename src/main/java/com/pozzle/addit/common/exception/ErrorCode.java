package com.pozzle.addit.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E000-1", "해당 유저를 찾을 수 없습니다."),

    RELAY_NOT_FOUND(HttpStatus.NOT_FOUND, "E001-1", "해당 릴레이를 찾을 수 없습니다."),

    TICKLE_NOT_FOUND(HttpStatus.NOT_FOUND, "E002-1", "해당 티클을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
