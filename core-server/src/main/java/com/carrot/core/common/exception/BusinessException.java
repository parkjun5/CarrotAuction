package com.carrot.core.common.exception;

import com.carrot.core.common.exception.code.ExceptionCode;
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
