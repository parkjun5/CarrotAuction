package com.carrot.auction.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "API 응답 헤더")
@Setter @Getter
@AllArgsConstructor
public class ApiResponseHeader {
    @Schema(description = "헤더 코드", example = "200")
    private int code;
    @Schema(description = "헤더 메시지", example = "SUCCESS")
    private String message;
}
