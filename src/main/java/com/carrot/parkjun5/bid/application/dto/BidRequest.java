package com.carrot.parkjun5.bid.application.dto;

import com.carrot.parkjun5.bid.application.annotation.BidRequestCheck;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

//TODO 유저 삭제 변경
// 비드 취소? 테스트 작성 필요
@BidRequestCheck
public record BidRequest(
        @Schema(description = "입찰자 아이디", example = "1")
        @NotNull Long bidderId,
        @Schema(description = "경매 아이디", example = "1")
        @NotNull Long auctionId,
        @Min(value = 1_000)
        @Schema(description = "입찰 가격", example = "50000")
        int biddingPrice,
        @Schema(description = "경매 종료 일자", type = "string", example = "2024-03-08T00:00:00+0900")
        @NotNull @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
        ZonedDateTime biddingTime) {
}
