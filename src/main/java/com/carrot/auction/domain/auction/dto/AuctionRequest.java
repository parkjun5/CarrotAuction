package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record AuctionRequest(
        @NotNull Long userId,
        @NotBlank @Size(max= 10) String name,
        String password,
        @Min(value= 0) @Max(value= 100)int limitOfEnrollment,
        @Min(value= 1_000) int biddingPrice,

        @Embedded Item item,
        @Enumerated(EnumType.STRING) Category category,
        @NotNull @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
        ZonedDateTime beginAuctionDateTime,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")  ZonedDateTime closeAuctionDateTime) {
}
