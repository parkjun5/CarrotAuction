package com.carrot.web_mvc.common.exception.handler;

import com.carrot.web_mvc.auction.exception.AuctionBusinessException;
import com.carrot.web_mvc.common.dto.ApiCommonResponse;
import com.carrot.web_mvc.common.exception.BusinessException;
import com.carrot.web_mvc.common.exception.code.ExceptionCode;
import com.carrot.web_mvc.common.exception.custom.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(basePackages = "com.carrot")
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
        return ResponseEntity.badRequest().body(new ExceptionDto(400, e.getMessage()));
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
    public ResponseEntity<Object> auctionBusinessException(AuctionBusinessException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiCommonResponse.fail(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> noSuchElementException(NoSuchElementException e) {
        log.error(e.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiCommonResponse.fail(404, "notFound", map));
    }

    @ExceptionHandler
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiCommonResponse.fail());
    }

    private Map<String, String> geInfoByException(BindException ex) {
        Map<String, String> map = new HashMap<>();
        ex.getBindingResult().getAllErrors();
        for (ObjectError allError : ex.getBindingResult().getAllErrors()) {
            map.put("error: ", allError.getDefaultMessage());
        }
        return map;
    }
}
