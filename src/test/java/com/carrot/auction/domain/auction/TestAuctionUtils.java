package com.carrot.auction.domain.auction;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.domain.entity.User;

import java.time.LocalDateTime;
import java.time.Month;

public interface TestAuctionUtils {

    default AuctionRoom getTestAuctionRoom() {
        AuctionRequest testAuctionRequest = getTestAuctionRequest();
        AuctionRoom auctionRoom = AuctionRoom.builder()
                .hostUser(getTestUser())
                .name(testAuctionRequest.name())
                .password(testAuctionRequest.password())
                .item(testAuctionRequest.item())
                .category(testAuctionRequest.category())
                .beginAuctionDateTime(testAuctionRequest.beginAuctionDateTime())
                .closeAuctionDateTime(testAuctionRequest.closeAuctionDateTime())
                .limitOfEnrollment(testAuctionRequest.limitOfEnrollment())
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

    default AuctionRequest getTestAuctionRequest() {
        return AuctionRequest.builder()
                .userId(1L)
                .name("테스트 경매장")
                .item(Item.of("맥북", 500_000, "신형 맥북 급처"))
                .password(null)
                .category(Category.DIGITAL)
                .limitOfEnrollment(100)
                .beginAuctionDateTime(LocalDateTime.of(2023, Month.of(2), 23, 10, 30))
                .closeAuctionDateTime(LocalDateTime.of(2999, Month.of(2), 23, 12, 30))
                .build();
    }
}
