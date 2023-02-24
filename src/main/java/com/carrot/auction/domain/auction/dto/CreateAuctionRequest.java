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
public record CreateAuctionRequest(
        @NotNull Long userId,
        @NotBlank String name,
        String password,
        @Min(value=0, message = "value must higher than 0" ) int limitOfEnrollment,
        @Embedded Item item,
        @Enumerated(EnumType.STRING) Category category,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime beginAuctionDateTime,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")  LocalDateTime closeAuctionDateTime) {
}
