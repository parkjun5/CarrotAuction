package com.carrot.auction.global.dto;

import java.util.HashMap;
import java.util.Map;

public record ApiResponse<T>(ApiResponseHeader header, Map<String, T> body) {

    private static final int SUCCESS = 200;
    private static final int FAILED = 500;
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다";

    public static <T> ApiResponse<T> success(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new ApiResponse<>(new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), map);
    }

    public static <T> ApiResponse<T> fail() {
        return new ApiResponse<>(new ApiResponseHeader(FAILED, FAILED_MESSAGE), null);
    }

    public static <T> ApiResponse<T>  fail(int errorCode, String errorMessage, Map<String, T> body) {
        return new ApiResponse<>(new ApiResponseHeader(errorCode, errorMessage), body);
    }
}
