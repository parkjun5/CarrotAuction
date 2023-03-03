package com.carrot.auction.global.exception.handler;

import com.carrot.auction.global.dto.ApiCommonResponse;
import com.carrot.auction.global.exception.BusinessException;
import com.carrot.auction.global.exception.code.ExceptionCode;
import com.carrot.auction.global.exception.custom.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.carrot.auction.domain")
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> businessExceptionHandler(BusinessException e) {
        log.error(e.getMessage());
        ExceptionCode exception = ExceptionCode.findByClass(e.getClass());
        return ResponseEntity.status(exception.getCode())
                .body(new ExceptionDto(exception.getCode(), exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionDto(403, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object processValidationError(MethodArgumentNotValidException ex) {
        ExceptionCode exceptionCode = ExceptionCode.findByClass(ex.getClass());
        return ResponseEntity.badRequest()
                .body(ApiCommonResponse.fail(
                        exceptionCode.getCode(),
                        exceptionCode.getMessage(),
                        geInfoByException(ex)));
    }

    @ExceptionHandler
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiCommonResponse.fail());
    }

    private Map<String, String> geInfoByException(BindException ex) {
        Map<String, String> map = new HashMap<>();
        for (FieldError allError : ex.getBindingResult().getFieldErrors()) {
            map.put(allError.getField(), allError.getDefaultMessage());
        }
        return map;
    }
}
