package io.hhplus.concert.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_USER(HttpStatus.BAD_REQUEST, "101", "유효하지 않은 사용자입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "102", "유효하지 않은 토큰입니다."),
    INVALID_POINT(HttpStatus.BAD_REQUEST, "103", "잘못된 금액입니다."),
    ALREADY_RESERVED(HttpStatus.BAD_REQUEST, "104", "예약되었거나 예약 진행중인 좌석입니다."),
    INVALID_PAYMENT_POINT(HttpStatus.BAD_REQUEST, "105", "잘못된 금액입니다."),
    INVALID_RESERVATION_STATUS(HttpStatus.BAD_REQUEST, "106", "잘못된 예약 상태입니다."),
    SHORTAGE_POINT(HttpStatus.BAD_REQUEST, "107", "잔액이 부족합니다."),
    NOT_AUTHORITY(HttpStatus.BAD_REQUEST, "108", "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
