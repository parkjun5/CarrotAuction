package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.auction.TestAuctionUtils;
import com.carrot.auction.domain.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

class AuctionRoomTest implements TestAuctionUtils {

    @Test
    @DisplayName("경매장 생성 파라미터가 널일 경우 IllegalArgumentException 발생")
    void auctionRoomNullCheck() {
        AuctionRoom.createByRequestBuilder noArgs = AuctionRoom.createByRequestBuilder();
        assertThatThrownBy(noArgs::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유저는 널일 수 없습니다.");
    }

    @Test
    @DisplayName("경매장 호스트 유저 널일 경우 IllegalArgumentException 발생")
    void auctionUserNull() {
        AuctionRoom.createByRequestBuilder noUserArg = AuctionRoom.createByRequestBuilder().auctionRequest(getTestAuctionRequest());
        assertThatThrownBy(noUserArg::build).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경매장 요청이 전부 널일 경우 IllegalArgumentException 발생")
    void auctionNoRequest() {
        User testUser = User.createUser().email("test").nickname("test").password("12").build();
        AuctionRoom.createByRequestBuilder noCreateAuctionRequest = AuctionRoom.createByRequestBuilder().hostUser(testUser);
        assertThatThrownBy(noCreateAuctionRequest::build).isInstanceOf(IllegalArgumentException.class);
    }

}