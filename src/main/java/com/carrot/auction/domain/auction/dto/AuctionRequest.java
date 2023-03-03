package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AuctionRequest(
        @NotNull Long userId,
        @NotBlank String name,
        String password,
        @Min(value=0, message = "value must higher than 0" ) int limitOfEnrollment,
        @Embedded Item item,
        @Enumerated(EnumType.STRING) Category category,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime beginAuctionDateTime,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd HH:mm")  LocalDateTime closeAuctionDateTime) {
    public void validateDateTime() {
        if (beginAuctionDateTime.isAfter(closeAuctionDateTime)) {
            throw new IllegalArgumentException("시작 날짜: " + beginAuctionDateTime + ", 종료 날짜: " + closeAuctionDateTime + "종료 날짜보다 시작 날짜가 이릅니다.");
        }
        if (LocalDateTime.now().isAfter(closeAuctionDateTime)) {
            throw new IllegalArgumentException("종료 날짜: " + closeAuctionDateTime + "이미 종료된 날짜입니다.");
        }
    }
}
