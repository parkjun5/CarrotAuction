package com.carrot.parkjun5.bidrule.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


//TODO 유저 삭제 변경
// 비드 취소?
// 비드 룰 만들기
// 옥션 방 옥션 분리
public record BidRuleBookRequest(
        @Schema(description = "비딩 룰 코드명", example = "ONE_CHANCE_RULE")
        @NotBlank String codeName,
        @Schema(description = "비딩 룰 설명", example = "단 한번만 입찰 가능")
        @NotBlank String description,

        @Schema(description = "비딩 룰 값", example = "1")
        int value) {

}
