package com.carrot.auction.domain.auction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record BiddingRequest(
        @NotNull Long bidderId,
        @Min(value = 1_000) int price,
        @NotNull @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
        ZonedDateTime biddingTime) {
    private static final BigDecimal MINIMUM_BIDDING_PERCENT = BigDecimal.valueOf(0.05);

    public static int getMinimumPrice(int existingPrice) {
        return existingPrice + MINIMUM_BIDDING_PERCENT.multiply(BigDecimal.valueOf(existingPrice)).intValue();
    }
}
