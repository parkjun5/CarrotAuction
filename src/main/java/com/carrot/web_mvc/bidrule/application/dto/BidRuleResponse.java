package com.carrot.web_mvc.bidrule.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public record BidRuleResponse(
        @Schema(description = "비딩 룰 코드", example = "ChanceRule, TargetAmountRule, TickIntervalRule, TimeLimitRule")
        @NotBlank String name,
        @Schema(description = "비딩 룰 설명", example = "단 한번만 입찰 가능")
        @NotBlank String description,

        @Schema(description = "비딩 룰 값", example = "0")
        String ruleValue) {
}
