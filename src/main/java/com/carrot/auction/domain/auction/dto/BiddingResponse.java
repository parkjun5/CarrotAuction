package com.carrot.auction.domain.auction.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public record BiddingResponse(
        String roomName,
        String bidderName,
        int price,
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
        ZonedDateTime biddingTime) {
}
