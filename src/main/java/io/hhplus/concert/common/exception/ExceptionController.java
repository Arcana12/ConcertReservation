package io.hhplus.concert.common.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("서버 에러가 발생했습니다 : " + e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", errorCode.getStatus());
        errorAttributes.put("code", errorCode.getCode());
        errorAttributes.put("message", errorCode.getMessage());

        return new ResponseEntity<>(errorAttributes, errorCode.getStatus());
    }
}
