package com.carrot.core.common.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionDto {

    private int code;
    private String message;
}
