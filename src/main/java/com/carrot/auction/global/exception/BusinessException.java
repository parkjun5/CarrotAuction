package com.carrot.auction.global.exception;

import com.carrot.auction.global.exception.code.ExceptionCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ExceptionCode codeAndMessage = ExceptionCode.findByClass(this.getClass());
    private final int code;
    private final String message;

    public BusinessException() {
        this.message = codeAndMessage.getMessage();
        this.code = codeAndMessage.getCode();
    }

}
