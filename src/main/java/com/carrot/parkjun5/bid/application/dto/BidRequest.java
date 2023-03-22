package com.carrot.parkjun5.bid.application.dto;

import com.carrot.parkjun5.bid.application.annotation.BidRequestCheck;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.ZonedDateTime;

//TODO 유저 삭제 변경
// 비드 취소?
// 비드 룰 만들기
// 옥션 방 옥션 분리
@BidRequestCheck
public record BidRequest(
        @Schema(description = "입찰자 아이디", example = "1")
        @NotNull Long bidderId,
        @Schema(description = "경매장 아이디", example = "1")
        @NotNull Long roomId,
        @Min(value = 1_000)
        @Schema(description = "입찰 가격", example = "50000")
        int biddingPrice,
        @Schema(description = "경매 종료 일자", type = "string", example = "2024-03-08T00:00:00+0900")
        @NotNull @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
        ZonedDateTime biddingTime) {
    private static final BigDecimal MINIMUM_BIDDING_PERCENT = BigDecimal.valueOf(0.05);

    public static int getMinimumPrice(int existingPrice) {
        return existingPrice + MINIMUM_BIDDING_PERCENT.multiply(BigDecimal.valueOf(existingPrice)).intValue();
    }
}
