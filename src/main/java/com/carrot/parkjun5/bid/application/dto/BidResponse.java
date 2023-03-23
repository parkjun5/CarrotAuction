package com.carrot.parkjun5.bid.application.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;


public record BidResponse(
        @Schema(description = "경매 이름", example = "맥북 / 14년식")
        String auctionName,
        @Schema(description = "입찰자 명", example = "wtbMacBook")
        String bidderName,
        @Schema(description = "입찰 가격", example = "50000")
        int biddingPrice,
        @Schema(description = "경매 종료 일자", type = "string", example = "2024-03-08T00:00:00+0900")
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
        ZonedDateTime biddingTime) {
}
