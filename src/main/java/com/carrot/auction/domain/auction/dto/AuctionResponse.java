package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
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

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Schema(description = "경매장 응답")
public record AuctionResponse (
        @Schema(description = "호스트 유저")
        @JsonSerialize(using = UserSerializer.class) User hostUser,
        @Schema(description = "참가자 리스트")
        @JsonSerialize(contentUsing = UserSerializer.class) List<User> participants,
        @Schema(description = "경매장 이름")
        String name,
        @Schema(description = "경매장 비밀번호")
        String password,
        @Schema(description = "경매장 최대 인원수")
        int limitOfEnrollment,
        @Schema(description = "경매 아이템")
        @Embedded Item item,
        @Schema(description = "경매 카테고리")
        @Enumerated(EnumType.STRING) Category category,
        @Schema(description = "경매 시작 일자")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime beginAuctionDateTime,
        @Schema(description = "경매 종료 일자")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")  LocalDateTime closeAuctionDateTime,
        @Schema(description = "경매장 상태")
        @Enumerated(EnumType.STRING) AuctionStatus auctionStatus) {

        public static AuctionResponse of(AuctionRoom auctionRoom) {
                return builder()
                        .name(auctionRoom.getName())
                        .item(auctionRoom.getItem())
                        .password(auctionRoom.getPassword())
                        .category(auctionRoom.getCategory())
                        .limitOfEnrollment(auctionRoom.getLimitOfEnrollment())
                        .beginAuctionDateTime(auctionRoom.getBeginAuctionDateTime())
                        .closeAuctionDateTime(auctionRoom.getCloseAuctionDateTime())
                        .auctionStatus(auctionRoom.getAuctionStatus())
                        .hostUser(auctionRoom.getHostUser())
                        .participants(auctionRoom.getParticipants())
                        .build();
        }

}
