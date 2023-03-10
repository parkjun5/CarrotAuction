package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.auction.domain.Bid;
import com.carrot.auction.domain.auction.domain.entity.AuctionStatus;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.serailizer.UserSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.Set;

@Builder
@Schema(description = "경매장 응답 객체")
public record AuctionResponse (
        @Schema(description = "경매장 아이디")
        long auctionRoomId,
        @Schema(description = "호스트 유저")
        @JsonSerialize(using = UserSerializer.class) User hostUser,
        @Schema(description = "참가자 닉네임")
        Set<String> nameOfParticipants,
        @Schema(description = "경매장 이름", example = "맥북 팝니다")
        String name,
        @Schema(description = "경매장 비밀번호", example = "q1w2e3!")
        String password,
        @Schema(description = "경매장 최대 인원수", example = "5")
        int limitOfEnrollment,
        @Embedded Bid bid,
        @Embedded Item item,
        @Schema(description = "카테고리", defaultValue = "DIGITAL")
        @Enumerated(EnumType.STRING) Category category,
        @Schema(description = "경매 시작 일자", type = "string", example = "2023-03-08T00:00:00+0900")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") ZonedDateTime beginDateTime,
        @Schema(description = "경매 종료 일자", type = "string", example = "2024-03-08T00:00:00+0900")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") ZonedDateTime closeDateTime,
        @Schema(description = "경매장 상태", defaultValue = "DRAFT")
        @Enumerated(EnumType.STRING) AuctionStatus auctionStatus) {
}
