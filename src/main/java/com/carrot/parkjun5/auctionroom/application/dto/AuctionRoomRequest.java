package com.carrot.parkjun5.auctionroom.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;


@Builder
@Schema(description = "경매장 요청 객체")
public record AuctionRoomRequest(
        @NotNull
        @Schema(description = "호스트유저 아이디", example = "1")
        Long userId,
        @NotBlank @Size(max= 10)
        @Schema(description = "경매장 이름", example = "맥북 팝니다")
        String name,
        @Schema(description = "비밀번호", example = "q1w2e3!")
        String password,
        @Min(value= 0) @Max(value= 100)
        @Schema(description = "경매장 최대 인원수", example = "5")
        int limitOfEnrollment){
}
