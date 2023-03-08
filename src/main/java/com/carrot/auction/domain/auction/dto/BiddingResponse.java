package com.carrot.auction.domain.auction.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;

public record BiddingResponse(
        @Schema(description = "경매장 이름", example = "맥북 팝니다")
        String roomName,
        @Schema(description = "입찰자 명", example = "wtbMacBook")
        String bidderName,
        @Schema(description = "입찰 가격", example = "50000")
        int price,
        @Schema(description = "경매 종료 일자", type = "string", example = "2024-03-08T00:00:00+0900")
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
        ZonedDateTime biddingTime) {
}
