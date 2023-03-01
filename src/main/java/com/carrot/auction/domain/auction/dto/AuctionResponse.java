package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.auction.domain.entity.AuctionStatus;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.serailizer.UserSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AuctionResponse (
        @JsonSerialize(using = UserSerializer.class) User hostUser,
        @JsonSerialize(contentUsing = UserSerializer.class) List<User> participants,
        String name,
        String password,
        int limitOfEnrollment,
        @Embedded Item item,
        @Enumerated(EnumType.STRING) Category category,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime beginAuctionDateTime,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")  LocalDateTime closeAuctionDateTime,
        @Enumerated(EnumType.STRING) AuctionStatus auctionStatus) {
}
