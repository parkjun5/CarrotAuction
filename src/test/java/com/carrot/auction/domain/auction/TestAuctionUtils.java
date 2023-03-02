package com.carrot.auction.domain.auction;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.dto.AuctionResponse;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.domain.entity.User;

import java.time.LocalDateTime;
import java.time.Month;

public interface TestAuctionUtils {

    default AuctionRoom getTestAuctionRoom() {
        AuctionRoom auctionRoom = AuctionRoom.createByRequestBuilder()
                .hostUser(getTestUser())
                .auctionRequest(getTestAuctionRequest())
                .build();
        auctionRoom.addParticipants(getTestUser());
        return auctionRoom;
    }

    default User getTestUser() {
        return User.createUser()
                .email("tester@gmail.com")
                .nickname("tester")
                .password("testPw")
                .build();
    }

    default AuctionResponse auctionRoomToResponse(AuctionRoom auctionRoom) {
        return AuctionResponse.builder()
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

    default AuctionRequest getTestAuctionRequest() {
        return AuctionRequest.builder()
                .userId(1L)
                .name("테스트 경매장")
                .item(Item.of("맥북", 500_000, "신형 맥북 급처"))
                .password(null)
                .category(Category.DIGITAL)
                .limitOfEnrollment(100)
                .beginAuctionDateTime(LocalDateTime.of(2023, Month.of(2), 23, 10, 30))
                .closeAuctionDateTime(LocalDateTime.of(2023, Month.of(2), 23, 12, 30))
                .build();
    }
}
