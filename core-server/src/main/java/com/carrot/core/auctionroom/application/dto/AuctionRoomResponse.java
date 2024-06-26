package com.carrot.core.auctionroom.application.dto;


import com.carrot.core.auctionroom.domain.AuctionRoom;
import com.carrot.core.user.application.dto.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;

@Builder
@Schema(description = "경매장 응답 객체")
public record AuctionRoomResponse(
        @Schema(description = "경매장 아이디")
        Long auctionRoomId,
        @Schema(description = "호스트 유저")
        UserResponse userResponse,
        @Schema(description = "참가자 닉네임")
        Set<String> nameOfParticipants,
        @Schema(description = "경매장 이름", example = "맥북 팝니다")
        String name,
        @Schema(description = "경매장 비밀번호", example = "q1w2e3!")
        String password,
        @Schema(description = "경매장 최대 인원수", example = "5")
        int limitOfEnrollment) {

    public static AuctionRoomResponse from(AuctionRoom auctionRoom, UserResponse userResponse,
                                           Set<String> nameOfParticipants) {
        return new AuctionRoomResponse(auctionRoom.getId(), userResponse, nameOfParticipants,
                auctionRoom.getName(), auctionRoom.getPassword(), auctionRoom.getLimitOfEnrollment());

    }
}
