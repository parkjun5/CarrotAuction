package com.carrot.parkjun5.bidrule.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public record BidRuleResponse(
        @Schema(description = "비딩 룰 코드", example = "ONE_CHANCE_RULE")
        @NotBlank String code,
        @Schema(description = "비딩 룰 설명", example = "단 한번만 입찰 가능")
        @NotBlank String description,

        @Schema(description = "비딩 룰 값", example = "1")
        int value) {
}
